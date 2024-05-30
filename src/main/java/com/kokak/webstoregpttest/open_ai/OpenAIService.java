package com.kokak.webstoregpttest.open_ai;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    private static final String API_KEY = "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";
    private static final Logger logger = LoggerFactory.getLogger(OpenAIService.class);
    private static final String PRODUCTS_ENDPOINT = "http://localhost:8080/api/products";

    public String generateProductRelatedResponse(String s) {


        // Fetch product data from your backend
        JSONArray productData;
        try {
            HttpResponse<String> productResponse = Unirest.get(PRODUCTS_ENDPOINT).asString();
            productData = new JSONArray(productResponse.getBody());
        } catch (Exception e) {
            logger.error("Failed to fetch products", e);
            return "Failed to fetch product information.";
        }

        // Construct the task message
        StringBuilder taskMessageBuilder = new StringBuilder("Act like a customer service chat-bot and answer questions only related to this clothing and accessories web-shop data, this is the products we have in stock, data: ");
        for (int i = 0; i < productData.length(); i++) {
            JSONObject product = productData.getJSONObject(i);
            JSONObject rating = product.getJSONObject("rating"); // Get the rating object
            double rate = rating.getDouble("rate"); // Extract the rate value
            int count = rating.getInt("count"); // Extract the count value

            // Append product details along with rating information to the task message
            taskMessageBuilder.append(String.format("\n- %s: $%.2f, Description: %s, Category: %s, Rating: %.1f (from %d reviews)",
                    product.getString("title"),
                    product.getDouble("price"),
                    product.getString("description"),
                    product.getString("category"),
                    rate, // Rating rate
                    count  // Rating count
            ));
        }

        // Append additional instructions after the loop
        taskMessageBuilder.append("\nOutput: You should always refuse to answer questions that are not related to this specific domain. If the input is not relevant, you will answer with: \"no comment.\". Answer questions related to the products that the customer asks for" );

        String taskMessage = taskMessageBuilder.toString();


        // Prepare the messages for the GPT model
        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant."));
        messages.put(new JSONObject().put("role", "user").put("content", taskMessage));

        try {
            HttpResponse<String> response = Unirest.post("https://api.openai.com/v1/chat/completions")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .body(new JSONObject()
                            .put("model", "gpt-3.5-turbo")
                            .put("messages", messages)
                            // .put("max_tokens", 150)
                            //.put("temperature", 0.7)
                            .toString())
                    .asString();

            logger.info("Response status: {}", response.getStatus());
            logger.info("Response body: {}", response.getBody());
            //logger.info("Response body: {}", response.getHeaders());

            JSONObject jsonResponse = new JSONObject(response.getBody());

            // Log the entire JSON response
            logger.info("OpenAI response: {}", jsonResponse.toString(4)); // Pretty print the JSON

            JSONArray choices = jsonResponse.getJSONArray("choices");

            if (jsonResponse.has("choices") && !jsonResponse.getJSONArray("choices").isEmpty()) {
                JSONObject firstChoice = jsonResponse.getJSONArray("choices").getJSONObject(0);

                if (firstChoice.has("message")) {
                    JSONObject message = firstChoice.getJSONObject("message");
                    if ("assistant".equals(message.getString("role"))) {
                        String messageContent = message.getString("content");
                        logger.info("OpenAI assistant message: {}", messageContent);
                        return messageContent; // Return the assistant's message content directly
                    }
                }
            }

            return "No response from GPT-3.5-turbo.";
        } catch (UnirestException e) {
            e.printStackTrace();
            return "Error calling OpenAI API";
        } // Missing closing brace for try block added here
    } // Missing closing brace for method added here
}
