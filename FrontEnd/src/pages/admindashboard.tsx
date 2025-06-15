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

function AdminDashboard() {
  const userInfo = useSelector((state: RootState) => state.auth.userInfo);
  const { data: timeSlots, isLoading } = useGetTutorTimeSlotsQuery(
    userInfo?.email_address ?? '',
    {
      skip: !userInfo?.email_address,
    }
  );

  
}

export default AdminDashboard;
