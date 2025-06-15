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
} from "@mui/material";
import { selectAuthSlice } from "../state/auth/slice";
import Layout from "../components/Layout";
import { createBooking, getBookingsByStudentId, deleteBooking } from "../services/booking.service";

interface TimeSlot {
  slotId: string; // UUID
  dayOfWeek: number;
  startTime: string;
  endTime: string;
}

interface Booking {
  bookingId: string;
  subjectName: string;
  bookingStatus: string;
  slot: TimeSlot;
}

export default function BookingPage() {
  const { userInfo } = useSelector((state) => selectAuthSlice(state));
  const [bookings, setBookings] = useState<Booking[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string>("");

  useEffect(() => {
    if (userInfo?.id) {
      fetchBookings();
    }
  }, [userInfo]);

  const fetchBookings = async () => {
    setLoading(true);
    try {
      const data = await getBookingsByStudentId(userInfo.id);
      setBookings(data);
    } catch (err: any) {
      setError(err.message || "Failed to fetch bookings");
    } finally {
      setLoading(false);
    }
  };

  // 示例：预约入口（实际应为弹窗或选择器）
  // const handleCreateBooking = async (slot: TimeSlot, subjectName: string) => { ... }

  const handleCancelBooking = async (bookingId: string) => {
    try {
      await deleteBooking(bookingId);
      fetchBookings();
    } catch (err: any) {
      setError(err.message || "Failed to cancel booking");
    }
  };

  if (loading) {
    return (
      <Layout isLoading={true}>
        <Container maxWidth="lg" sx={{ py: 4 }}>
          <CircularProgress />
        </Container>
      </Layout>
    );
  }

  return (
    <Layout isLoading={false}>
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Typography variant="h4" gutterBottom>
          My Bookings
        </Typography>
        {error && <Alert severity="error">{error}</Alert>}
        <Grid container spacing={3}>
          {bookings.map((booking) => (
            <Grid item={true} xs={12} md={6} key={booking.bookingId}>
              <Card>
                <CardContent>
                  <Typography variant="h6">{booking.subjectName}</Typography>
                  <Typography>Status: {booking.bookingStatus}</Typography>
                  <Typography>
                    Time: {booking.slot.dayOfWeek} {booking.slot.startTime} - {booking.slot.endTime}
                  </Typography>
                  <Button
                    variant="outlined"
                    color="error"
                    onClick={() => handleCancelBooking(booking.bookingId)}
                  >
                    Cancel Booking
                  </Button>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Container>
    </Layout>
  );
}
