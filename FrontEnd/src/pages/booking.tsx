import React from "react";
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
import { Add as AddIcon, Delete as DeleteIcon, Edit as EditIcon } from "@mui/icons-material";

interface TimeSlot {
  slotId: string; // UUID
  dayOfWeek: number;
  startTime: string;
  endTime: string;
}

const handleCancelBooking = async (bookingId: string) => {
  console.log(`Cancel booking with ID: ${bookingId}`);
};

const BookingPage: React.FC = () => {
  const { userInfo, isLoggedIn } = useSelector((state) => selectAuthSlice(state));
  const {
    data: bookings = [],
    error,
    isLoading,
  } = useGetBookingByEmailQuery(userInfo?.email_address ?? "", {
    skip: !isLoggedIn || !userInfo?.email_address,
  });

  return (
    <Layout isLoading={isLoading}>
      <Container
        sx={{ mt: 4, mb: 4}} // Set a custom large width
      >
        <Paper
          sx={{
            width: "600px", // Ensure Paper takes full width of Container
            maxWidth: "none", // Remove maxWidth restriction
            padding: "24px",
          }}
          elevation={3}
        >
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
                            color={booking.bookingStatus === "Confirmed" ? "success" : "default"}
                            size="small"
                          />
                          <Button
                            variant="outlined"
                            color="error"
                            size="small"
                            startIcon={<DeleteIcon />}
                            onClick={() => handleCancelBooking(booking.id)}
                          >
                            Cancel
                          </Button>
                        </Box>
                      }
                      secondary={`Date: ${new Date(booking.bookingDate).toLocaleDateString()
                        }, Subject: ${booking.subjectName}`}
                      
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
};

export default BookingPage;