import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import {
  Container,
  Typography,
  Box,
  Card,
  CardContent,
  Grid,
  Button,
  Chip,
  Stack,
  Divider,
  Alert,
  CircularProgress,
  Paper,
  List,
  ListItem,
  ListItemText,
} from "@mui/material";
import { selectAuthSlice } from "../state/auth/slice";
import Layout from "../components/Layout";
import { useGetBookingByEmailQuery } from "../services/booking.service";  
import { Add as AddIcon, Delete as DeleteIcon, Edit as EditIcon } from '@mui/icons-material';
import { RootState } from '../state/store';


interface TimeSlot {
  slotId: string; // UUID
  dayOfWeek: number;
  startTime: string;
  endTime: string;
}

interface Booking{
 id: string;
  bookingDate: string;
  bookingStatus: string;
  subjectName: string;
  numberOfSession:number;
  studentName: string;
  timeslots: string;
  tutorName: string;
}

const handleCancelBooking = async (bookingId: string) => {
  // Implement the logic to cancel the booking
  console.log(`Cancel booking with ID: ${bookingId}`);
  // You can call an API or perform any other action here
};


const BookingPage: React.FC = () => {
  const { userInfo, isLoggedIn } = useSelector((state) => selectAuthSlice(state));
  const {
    data: bookings = [],
    error,
    isLoading,
  } = useGetBookingByEmailQuery(userInfo?.email_address ?? '', {
    skip: !isLoggedIn || !userInfo?.email_address, // Skip if not logged in or email is not available
  });

return (
     <Layout isLoading={isLoading}>
      <Container  sx={{ mt: 4, mb: 4 }}>
        <Paper sx={{ p: 2,mb:4 }}>
        <Typography variant="h5" gutterBottom>
          My Bookings
        </Typography>
        <List>
          {bookings.length === 0 ? (
            <Typography variant="body1" color="textSecondary" align="center">
              No bookings found
            </Typography>
          ) : (
            bookings.map((booking) => (
              <React.Fragment key={booking.id}>
                <ListItem>
                  <ListItemText
                    primary={
                      <Box display="flex" justifyContent="space-between" alignItems="center">
                        <Typography variant="subtitle1">
                          Booking with Tutor {booking.tutorName}
                        </Typography>
                        <Chip 
                          label={booking.bookingStatus}
                          color={booking.bookingStatus === 'Confirmed' ? 'success' : 'default'}
                          size="small"
                        />
                      </Box>
                    }
                    secondary={`Date: ${new Date(booking.bookingDate).toLocaleDateString()}`}
                  />
                </ListItem>
                <Divider />
              </React.Fragment>
            ))
          )}
        </List>
      </Paper>
    </Container>
    </Layout>
  );
}
export default BookingPage;