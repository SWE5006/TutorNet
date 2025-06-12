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
} from "@mui/material";
import Layout from "../components/Layout";
import { useGetTutorTimeSlotsQuery } from "../services/tutor.service";
import { useSelector } from "react-redux";
import { RootState } from "../state/store";

function TutorDashboard() {
  const userInfo = useSelector((state: RootState) => state.auth.userInfo);
  const { data: timeSlots, isLoading } = useGetTutorTimeSlotsQuery(
    userInfo?.email_address ?? '',
    {
      skip: !userInfo?.email_address,
    }
  );

  return (
    <Layout isLoading={isLoading}>
      <Container maxWidth="xl" sx={{ mt: 4, mb: 4 }}>
        {/* First Row */}
        <Grid container spacing={3} sx={{ mb: 4 }}>
          <Grid>
            <Paper
              sx={{
                p: 2,
                display: 'flex',
                flexDirection: 'column',
                minHeight: 240,
              }}
            >
              <Typography variant="h6" sx={{ mb: 2 }}>
                Available Time Slots
              </Typography>
              <List>
                {timeSlots?.map((slot, index) => (
                  <React.Fragment key={index}>
                    <ListItem>
                      <ListItemText
                        primary={`${slot.dayOfWeek}`}
                        secondary={`${slot.startTime} - ${slot.endTime} (${slot.status})`}
                      />
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
                display: 'flex',
                flexDirection: 'column',
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
                display: 'flex',
                flexDirection: 'column',
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
        </Grid>
      </Container>
    </Layout>
  );
}

export default TutorDashboard;
