import React, { useState, useEffect } from "react";
import {
  Container,
  Typography,
  Box,
  Grid,
  Paper,
  List,
  ListItem,
  ListItemText,
  Divider,
  IconButton,
} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import DeleteIcon from "@mui/icons-material/Delete";
import Layout from "../components/Layout";
import {
  useGetTutorTimeSlotsByUserIdQuery,
  useAddTimeSlotMutation,
  useDeleteTimeSlotMutation,
} from "../services/tutor.service";
import { useSelector } from "react-redux";
import { selectAuthSlice } from "../state/auth/slice";
import AddTimeslotModal from "../components/AddTimeSlot";

function TutorDashboard() {
  const { userInfo } = useSelector((state) => selectAuthSlice(state));
  const [timeslotOpen, setTimeSlotOpen] = useState(false);

  const { data: timeSlots, isLoading } = useGetTutorTimeSlotsByUserIdQuery(
    userInfo?.email_address ?? "",
    {
      skip: !userInfo?.email_address,
    }
  );
  const [addTimeSlot, addResult] = useAddTimeSlotMutation();
  const [deleteTimeSlot, deleteResult] = useDeleteTimeSlotMutation();

  useEffect(() => {
    if (addResult.isSuccess) {
      setTimeSlotOpen(false);
    }
  }, [addResult]);

  return (
    <Layout isLoading={isLoading}>
      <Container maxWidth="xl" sx={{ mt: 4, mb: 4 }}>
        {/* First Row */}
        <Grid container spacing={3} sx={{ mb: 4 }}>
          <Grid>
            <Paper
              sx={{
                p: 2,
                display: "flex",
                flexDirection: "column",
                minHeight: 240,
              }}
            >
              <Box
                sx={{
                  display: "flex",
                  "justify-content": "space-between",
                  "align-items": "center",
                }}
              >
                <Typography variant="h6">Time Slots</Typography>
                <IconButton
                  aria-label="add"
                  onClick={() => {
                    setTimeSlotOpen(true);
                  }}
                >
                  <AddIcon />
                </IconButton>
              </Box>
              <List>
                {timeSlots?.map((slot, index) => (
                  <React.Fragment key={index}>
                    <ListItem>
                      <ListItemText
                        primary={`${slot.dayOfWeek}`}
                        secondary={`${slot.startTime} - ${slot.endTime} (${slot.status})`}
                      />
                      <IconButton
                        aria-label="delete"
                        onClick={() => {
                          deleteTimeSlot(slot.id || "");
                        }}
                      >
                        <DeleteIcon />
                      </IconButton>
                    </ListItem>
                    {index < timeSlots.length - 1 && <Divider />}
                  </React.Fragment>
                ))}
                {(!timeSlots || timeSlots.length === 0) && (
                  <ListItem>
                    <ListItemText primary="No time slots available" />
                  </ListItem>
                )}
              </List>
            </Paper>
          </Grid>
        </Grid>

        {/* Second Row */}
        <Grid container spacing={3}>
          {/* Upcoming Sessions */}
          <Grid>
            <Paper
              sx={{
                p: 2,
                display: "flex",
                flexDirection: "column",
                minHeight: 240,
              }}
            >
              <Typography variant="h6" sx={{ mb: 2 }}>
                Upcoming Sessions
              </Typography>
              <List>
                {/* Replace with actual upcoming sessions data */}
                <ListItem>
                  <ListItemText
                    primary="Mathematics with Alice"
                    secondary="Thursday, 3:00 PM"
                  />
                </ListItem>
                <Divider />
              </List>
            </Paper>
          </Grid>

          {/* Confirmed Students */}
          <Grid>
            <Paper
              sx={{
                p: 2,
                display: "flex",
                flexDirection: "column",
                minHeight: 240,
              }}
            >
              <Typography variant="h6" sx={{ mb: 2 }}>
                Confirmed Students
              </Typography>
              <List>
                <ListItem>
                  <ListItemText
                    primary="Alice Smith"
                    secondary="Mathematics - Weekly Sessions"
                  />
                </ListItem>
                <Divider />
              </List>
            </Paper>
          </Grid>
          <AddTimeslotModal
            open={timeslotOpen}
            onClose={() => {
              setTimeSlotOpen(false);
            }}
            onSave={(v) => {
              addTimeSlot(v);
            }}
          />
        </Grid>
      </Container>
    </Layout>
  );
}

export default TutorDashboard;
