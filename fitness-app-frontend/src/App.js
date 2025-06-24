import {
  Navigate,
  redirect,
  Route,
  BrowserRouter as Router,
  Routes,
} from "react-router";
import "./App.css";
import { Box, Button, Typography } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import { useDispatch } from "react-redux";
import { setCredentials } from "./store/authSlice";
import ActivitiesPage from "./components/ActivitiesPage";
import ActivityDetails from "./components/ActivityDetails";

function App() {
  const { token, tokenData, logIn, logOut, isAuthenticated } =
    useContext(AuthContext);

  const dispatch = useDispatch();
  const [authReady, setAuthReady] = useState(false);

  useEffect(() => {
    if (token) {
      dispatch(setCredentials({ token, user: tokenData }));
      setAuthReady(true);
    }
  }, [token, tokenData, dispatch]);

  return (
    <Router>
      {!token ? (
        <Box
          sx={{
            minHeight: "70vh",
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
            gap: 3,
            bgcolor: "#fff0f3",
            borderRadius: 3,
            boxShadow: 4,
            mx: "auto",
            maxWidth: 400,
            mt: 10,
            p: 4,
          }}
        >
          <Typography
            variant="h3"
            sx={{ color: "#dc004e", fontWeight: "bold" }}
          >
            Welcome!
          </Typography>
          <Typography
            variant="body1"
            sx={{ color: "#333", mb: 2, textAlign: "center" }}
          >
            Sign in to track your fitness activities and reach your goals.
          </Typography>
          <Button
            variant="contained"
            onClick={() => logIn()}
            sx={{
              backgroundColor: "#dc004e",
              color: "#fff",
              paddingX: 3,
              paddingY: 1.5,
              borderRadius: "8px",
              textTransform: "none",
              fontWeight: "bold",
              boxShadow: 3,
              "&:hover": {
                backgroundColor: "#b3003c",
                boxShadow: 5,
              },
            }}
          >
            Login
          </Button>
        </Box>
      ) : (
        // <pre>{JSON.stringify(tokenData, null, 2)}</pre>
        <>
          <Box sx={{ p: 2, border: "1px dashed grey" }}>
            <Button variant="contained" color="secondary" onClick={logOut}>
              logOut
            </Button>
          </Box>
          <Routes>
            <Route path="/activities" element={<ActivitiesPage />} />
            <Route path="/activities/:id" element={<ActivityDetails />} />
            <Route
              path="/"
              element={
                token ? (
                  <Navigate to={"/activities"} />
                ) : (
                  <div>Please login to access your activities !</div>
                )
              }
            />
          </Routes>
        </>
      )}
    </Router>
  );
}

export default App;
