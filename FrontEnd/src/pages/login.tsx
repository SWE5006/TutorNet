import React, { useEffect, useState, useCallback } from "react";
import {
  Divider,
  Link,
  Button,
  TextField,
  Box,
  Container,
  Paper,
  Typography,
} from "@mui/material";
import { useLoginMutation } from "../services/auth.service";
import { selectAuthSlice } from "../state/auth/slice";
import { useSelector } from "react-redux";
import { navigate } from "gatsby";
import { Box as MuiBox } from "@mui/material"; // Rename to avoid confusion

const authUrl = process.env.GATSBY_BACKEND_API_URL;
const Login = () => {
  const [requestLogin, result] = useLoginMutation();
  const { isLoggedIn } = useSelector((state) => selectAuthSlice(state));
  const [emailAddress, setEmailAddress] = useState("");
  const [password, setPassword] = useState("");

  const onLogin = useCallback(() => {
    requestLogin({ emailAddress, password });
  }, [emailAddress, password]);

  const onLoginWithGoogle = useCallback(() => {
    // Auth backend will redirect to Google
    window.location.href = `${authUrl}/oauth2/authorization/google`;
  }, []);

  useEffect(() => {
    if (isLoggedIn) {
      navigate("/home");
    }
  }, [isLoggedIn]);

  return isLoggedIn ? null : (
    <Container maxWidth="lg" sx={{ height: "100vh", display: "flex", alignItems: "center" }}>
      <MuiBox
        sx={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          width: "100%",
          bgcolor: "background.paper",
          borderRadius: 3,
          boxShadow: 3,
          overflow: "hidden",
        }}
      >
        {/* Left side - Image */}
        <MuiBox
          sx={{
            flex: 1,
            height: "600px",
            display: { xs: "none", md: "block" }, // Hide on mobile
          }}
        >
          <img
            src="/images/loginpic.jpg"
            alt="Login"
            style={{
              width: "100%",
              height: "100%",
              objectFit: "cover",
            }}
          />
        </MuiBox>

      
        <MuiBox
          sx={{
            flex: 1,
            p: 4,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
        

          <Paper
            elevation={0}
            sx={{
              p: 4,
              width: "100%",
              maxWidth: 400,
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
              borderRadius: 2,
            }}
          >
             <img 
      src="/images/logo_sidebar_small.png" 
      alt="TutorNet Logo"
      style={{
        width: '60px',
        height: '60px',
        objectFit: 'contain'
      }}
    />
          <Typography
            variant="h4"
            sx={{
              mb: 4,
              fontWeight: "bold",
              color: "info.light", // Light blue color from MUI theme
              textAlign: "center",
            }}
          >
            Welcome to TutorNet
          </Typography>
            <Box component="form" noValidate sx={{ mt: 1, width: "100%" }}>
              <TextField
                margin="normal"
                required
                fullWidth
                id="email"
                label="Email Address"
                name="email"
                autoComplete="email"
                autoFocus
                value={emailAddress}
                onChange={(e) => setEmailAddress(e.target.value)}
                sx={{ mb: 2 }}
              />
              <TextField
                margin="normal"
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="current-password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                sx={{ mb: 3 }}
              />
              <Button
                fullWidth
                variant="contained"
                onClick={onLogin}
                sx={{ mt: 1, mb: 2, py: 1.5 }}
              >
                Login
              </Button>
              {result.isError && (
                <Typography color="error" sx={{ mt: 1 }}>
                  Login failed. Please try again.
                </Typography>
              )}
              <Divider sx={{ my: 3 }}>OR</Divider>
              <Button
                fullWidth
                variant="outlined"
                onClick={onLoginWithGoogle}
                startIcon={
                  <img
                    src="/google.svg"
                    alt="Google Logo"
                    style={{ width: 20, marginRight: 10 }}
                  />
                }
                sx={{ mb: 2, py: 1.5 }}
              >
                Login with Google
              </Button>
              <Box
                sx={{
                  display: "flex",
                  justifyContent: "space-between",
                  width: "100%",
                  mt: 2,
                }}
              >
                <Typography>Don't have an account?</Typography>
                <Typography>
                  <Link href="/signupstudent" variant="body2">
                    Sign Up as Student
                  </Link>
                </Typography>

                <Typography>
                  <Link href="/signuptutor" variant="body2">
                    Sign Up as Tutor
                  </Link>
                </Typography>
              </Box>
            </Box>
          </Paper>
        </MuiBox>
      </MuiBox>
    </Container>
  );
};

export default Login;
