import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import {
  Container,
  Paper,
  Typography,
  List,
  ListItem,
  ListItemText,
  Box,
  Chip,
  Divider,
  Alert,
  Button,
  CircularProgress,
} from "@mui/material";
import { selectAuthSlice } from "../state/auth/slice";
import Layout from "../components/Layout";
import { useGetBookingByEmailQuery } from "../services/tutor.service";


import { Add as AddIcon, Delete as DeleteIcon, Edit as EditIcon } from "@mui/icons-material";

// Define the Booking type to match the booking object structure
interface Booking {
  id: string;
  bookingDate: string;
  bookingStatus: string;
  subjectName: string;
  numberOfSession: number;
  studentName: string;
  timeslots: string;
  tutorName: string;
}


const handleCancelBooking = async (bookingId: string) => {
  console.log(`Cancel booking with ID: ${bookingId}`);
};

const BookingPage: React.FC = () => {
  const { userInfo, isLoggedIn } = useSelector((state) => selectAuthSlice(state));
  const {
    data: bookings,
    error,
    isLoading,
    refetch
  } = useGetBookingByEmailQuery(userInfo?.email_address ?? "", {
    skip: !isLoggedIn || !userInfo?.email_address,
    refetchOnMountOrArgChange:true
  });

  // Refetch data when component mounts or page is navigated to
  useEffect(() => {
    if (userInfo?.email_address) {
      // refetch();
    }
  }, [userInfo?.email_address, refetch]);

  if (isLoading) {
    return (
      <Container maxWidth="lg">
        <Paper elevation={3} sx={{ p: 3, my: 2, textAlign: 'center' }}>
          <CircularProgress />
          <Typography variant="h6" sx={{ mt: 2 }}>
            Loading bookings...
          </Typography>
        </Paper>
      </Container>
    );
  }

  if (error) {
    return (
      <Container maxWidth="lg">
        <Paper elevation={3} sx={{ p: 3, my: 2 }}>
          <Alert severity="error">
            Error loading bookings. Please try again.
          </Alert>
        </Paper>
      </Container>
    );
  }

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
            {!bookings || bookings.length === 0 ? (
              <Typography variant="body1" color="textSecondary" align="center">
                No bookings found
              </Typography>
            ) : (
              bookings.map((booking: Booking) => (
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
                      secondary={`Date: ${new Date(booking.bookingDate).toLocaleDateString()}, Subject: ${booking.subjectName}`}
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
