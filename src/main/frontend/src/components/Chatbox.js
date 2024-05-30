import React, { useState } from 'react';
import { TextField, Button, Box, Paper, Typography, List, ListItem } from '@mui/material';

const Chatbox = () => {
  const [message, setMessage] = useState('');
  const [conversation, setConversation] = useState([]);

  const sendMessage = async () => {
    const userMessage = { role: 'user', content: message };
    setConversation([...conversation, userMessage]);

    const response = await fetch('http://localhost:8080/api/chat', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ message })
    });

    const data = await response.text(); // Adjust according to how your backend sends back the response
    const aiMessage = { role: 'ai', content: data };
    setConversation([...conversation, userMessage, aiMessage]);
    setMessage(''); // Clear input after sending
  };

  return (
    <Paper elevation={3} sx={{ p: 2, position: 'absolute', bottom: 0, right: 0, minWidth: 800, maxHeight: 500, display: 'flex', flexDirection: 'column' }}>
      <Box sx={{ overflowY: 'scroll', flexGrow: 1 }}>
        <List>
          {conversation.map((msg, index) => (
            <ListItem key={index}>
              <Typography color={msg.role === 'user' ? 'primary' : 'secondary'}>
                {msg.role}: {msg.content}
              </Typography>
            </ListItem>
          ))}
        </List>
      </Box>
      <Box sx={{ display: 'flex', alignItems: 'center' }}>
        <TextField
          fullWidth
          variant="outlined"
          size="small"
          placeholder="Type a message..."
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          onKeyPress={(e) => e.key === 'Enter' && sendMessage()}
          sx={{ mr: 1 }}
        />
        <Button variant="contained" onClick={sendMessage}>Send</Button>
      </Box>
    </Paper>
  );
};

export default Chatbox;
