import React, { useState, useEffect } from "react";
import {
  Container,
  Paper,
  Typography,
  List,
  ListItem,
  ListItemText,
  Box,
  Chip,
} from "@mui/material";
import { useSelector } from "react-redux";
import { RootState } from '../state/store';

const BookingPage: React.FC = () => {



  

  const [bookings, setBookings] = useState<any[]>([]);
  const currentUser = useSelector((state: RootState) => state.auth.userInfo);

  

  const getStatusColor = (status: string) => {
    switch (status.toLowerCase()) {
      case "confirmed":
        return "success";
      case "pending":
        return "warning";
      case "cancelled":
        return "error";
      default:
        return "default";
    }
  };

  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Paper elevation={3} sx={{ p: 3 }}>
        <Typography variant="h4" gutterBottom>
          My Bookings
        </Typography>
        <List>
          {bookings.length === 0 ? (
            <Typography color="textSecondary" align="center">
              No bookings found
            </Typography>
          ) : (
            bookings.map((booking, index) => (
              <React.Fragment key={booking.id}>
                <ListItem alignItems="flex-start">
                  <ListItemText
                    primary={
                      <Box display="flex" justifyContent="space-between" alignItems="center">
                        <Typography variant="h6">Booking #{booking.id.slice(-4)}</Typography>
                        <Chip
                          label={booking.status}
                          color={getStatusColor(booking.status) as any}
                          size="small"
                        />
                      </Box>
                    }
                    secondary={
                      <Box sx={{ mt: 1 }}>
                        <Typography component="span" variant="body2" color="text.primary">
                          Date: {new Date(booking.date).toLocaleDateString()}
                        </Typography>
                        
                        
                        <br />
                        <Typography component="span" variant="body2">
                          Tutor ID: {booking.tutorId}
                        </Typography>
                      </Box>
                    }
                  />
                </ListItem>
                {index < booking.length - 1  && <hr />}
              </React.Fragment>
            ))
          )}
          
        </List>
      </Paper>
    </Container>
  );
};

export default BookingPage;



