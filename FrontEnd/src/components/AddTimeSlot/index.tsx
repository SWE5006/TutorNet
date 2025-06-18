import React, { useState } from "react";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
  MenuItem,
  FormControl,
  InputLabel,
  Select,
  Box,
  Typography,
} from "@mui/material";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { TimePicker } from "@mui/x-date-pickers/TimePicker";
import dayjs, { Dayjs } from "dayjs";
import type { TutorTimeSlot } from "../../types/types";
import isSameOrAfter from "dayjs/plugin/isSameOrAfter";

dayjs.extend(isSameOrAfter);

// Define the shape of a Timeslot
// interface Timeslot {
//   day: string;
//   startTime: string; // Storing as "HH:mm" string
//   endTime: string; // Storing as "HH:mm" string
// }

// Props for the AddTimeslotModal component
interface AddTimeslotModalProps {
  open: boolean; // Controls if the modal is open or closed
  onClose: () => void; // Function to call when the modal is closed
  onSave: (timeslot: TutorTimeSlot) => void; // Function to call when save button is clicked
}

const daysOfWeek = [
  "Monday",
  "Tuesday",
  "Wednesday",
  "Thursday",
  "Friday",
  "Saturday",
  "Sunday",
];

const AddTimeslotModal: React.FC<AddTimeslotModalProps> = ({
  open,
  onClose,
  onSave,
}) => {
  const [selectedDay, setSelectedDay] = useState<string>("");
  const [startTime, setStartTime] = useState<Dayjs | null>(
    dayjs().startOf("hour")
  ); // Default to current hour
  const [endTime, setEndTime] = useState<Dayjs | null>(
    dayjs().startOf("hour").add(1, "hour")
  ); // Default to next hour
  const [errors, setErrors] = useState<{
    dayOfWeek?: string;
    startTime?: string;
    endTime?: string;
  }>({});

  const validateForm = () => {
    const newErrors: { day?: string; startTime?: string; endTime?: string } =
      {};
    let isValid = true;

    if (!selectedDay) {
      newErrors.day = "Day is required";
      isValid = false;
    }
    if (!startTime) {
      newErrors.startTime = "Start time is required";
      isValid = false;
    }
    if (!endTime) {
      newErrors.endTime = "End time is required";
      isValid = false;
    }
    //@ts-ignore
    if (startTime && endTime && startTime.isSameOrAfter(endTime)) {
      newErrors.endTime = "End time must be after start time";
      isValid = false;
    }

    setErrors(newErrors);
    return isValid;
  };

  const handleSave = () => {
    if (validateForm()) {
      const newTimeslot: TutorTimeSlot = {
        dayOfWeek: selectedDay,
        startTime: startTime ? startTime.format("HH:mm") : "", // Format to "HH:mm"
        endTime: endTime ? endTime.format("HH:mm") : "", // Format to "HH:mm"
        status: "AVAILABLE",
      };
      onSave(newTimeslot);
      // Reset form fields after successful save
      setSelectedDay("");
      setStartTime(dayjs().startOf("hour"));
      setEndTime(dayjs().startOf("hour").add(1, "hour"));
      setErrors({}); // Clear errors
    }
  };

  const handleClose = () => {
    // Optionally reset fields and errors on close if desired
    setSelectedDay("");
    setStartTime(dayjs().startOf("hour"));
    setEndTime(dayjs().startOf("hour").add(1, "hour"));
    setErrors({});
    onClose();
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>Add New Timeslot</DialogTitle>
      <DialogContent>
        <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
          Select a day and specify the start and end times for your
          availability.
        </Typography>
        <FormControl fullWidth margin="normal" error={!!errors.dayOfWeek}>
          <InputLabel id="day-select-label">Day of Week</InputLabel>
          <Select
            labelId="day-select-label"
            id="day-select"
            value={selectedDay}
            label="Day of Week"
            onChange={(e) => setSelectedDay(e.target.value)}
          >
            {daysOfWeek.map((day) => (
              <MenuItem key={day} value={day}>
                {day}
              </MenuItem>
            ))}
          </Select>
          {errors.dayOfWeek && (
            <Typography color="error" variant="caption">
              {errors.dayOfWeek}
            </Typography>
          )}
        </FormControl>

        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <Box sx={{ display: "flex", gap: 2, mt: 2 }}>
            <FormControl fullWidth margin="normal" error={!!errors.startTime}>
              <TimePicker
                label="Start Time"
                value={startTime}
                onChange={(newValue) => setStartTime(dayjs(newValue))}
                slotProps={{
                  textField: {
                    fullWidth: true,
                    error: !!errors.startTime,
                    helperText: errors.startTime,
                  },
                }}
              />
            </FormControl>
            <FormControl fullWidth margin="normal" error={!!errors.endTime}>
              <TimePicker
                label="End Time"
                value={endTime}
                onChange={(newValue) => setEndTime(dayjs(newValue))}
                slotProps={{
                  textField: {
                    fullWidth: true,
                    error: !!errors.endTime,
                    helperText: errors.endTime,
                  },
                }}
              />
            </FormControl>
          </Box>
        </LocalizationProvider>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} color="secondary">
          Cancel
        </Button>
        <Button onClick={handleSave} variant="contained" color="primary">
          Save Timeslot
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default AddTimeslotModal;
