import React from "react";




import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Box,
  CircularProgress,
  Container,
} from "@mui/material";
import { navigate } from "gatsby";
import { selectAuthSlice } from "../../state/auth/slice";
import { useLogoutMutation } from "../../services/auth.service";
import { useSelector } from "react-redux";

interface LayoutProps {
  children: React.ReactNode;
  isLoading: boolean;
}

const Layout: React.FC<LayoutProps> = ({ children, isLoading = false }) => {
  const [requestLogout, result] = useLogoutMutation();
  const { userInfo } = useSelector((state) => selectAuthSlice(state));
  const performLogout = () => {
    requestLogout();
  };


  
  React.useEffect(() => {
    if (result.isSuccess) {
      navigate("/login");
    }
  }, [result]);
  return (
    <Box sx={{ display: "flex", flexDirection: "column", minHeight: "100vh" }}>
      {/* Header */}
  



      <Container component="main" sx={{ flexGrow: 1, py: 4 }}>
        {isLoading ? (
          <Box
            sx={{
              display: "flex",
              flexDirection: "column",
              justifyContent: "center",
              alignItems: "center",
              minHeight: "calc(100vh - 128px)",
            }}
          >
            <CircularProgress size={60} sx={{ mb: 2 }} />
            <Typography variant="h6" color="text.secondary">
              Loading...
            </Typography>
          </Box>
        ) : (
          children
        )}
      </Container>
    </Box>
  );
};

export default Layout;
