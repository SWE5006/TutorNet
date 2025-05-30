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
    <Container component="main" maxWidth="xs">
      <Paper
        elevation={6}
        sx={{
          mt: 8,
          p: 4,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          borderRadius: 2,
        }}
      >
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
            <Typography><Link href="/signupstudent" variant="body2">
              Sign Up as Student
            </Link></Typography>
            
            <Typography><Link href="/signuptutor" variant="body2">
              Sign Up as Tutor
            </Link></Typography>
          </Box>
        </Box>
      </Paper>
    </Container>
  );
};
export default Login;
