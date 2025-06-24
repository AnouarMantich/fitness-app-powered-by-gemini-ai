import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import {
  Typography,
  Card,
  CardContent,
  Divider,
  Box,
  Avatar,
  Stack,
  CircularProgress,
  Paper,
} from "@mui/material";

import AccessTimeIcon from "@mui/icons-material/AccessTime";
import WhatshotIcon from "@mui/icons-material/Whatshot";
import FitnessCenterIcon from "@mui/icons-material/FitnessCenter";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import LightbulbIcon from "@mui/icons-material/Lightbulb";
import SafetyCheckIcon from "@mui/icons-material/SafetyCheck";
import TipsAndUpdatesIcon from "@mui/icons-material/TipsAndUpdates";

import { getActivityDetail } from "../services/api";

const ActivityDetails = () => {
  const { id } = useParams();
  const [activity, setActivity] = useState(null);

  useEffect(() => {
    const fetchActivityDetail = async () => {
      try {
        const response = await getActivityDetail(id);
        setActivity(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchActivityDetail();
  }, [id]);

  if (!activity) {
    return (
      <Box display="flex" justifyContent="center" mt={6}>
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box sx={{ maxWidth: 900, mx: "auto", p: 3 }}>
      {/* Section: Title */}
      <Typography variant="h4" fontWeight="bold" mb={3} textAlign="center">
        Activity Summary
      </Typography>

      {/* Section: Activity Overview */}
      <Paper elevation={4} sx={{ p: 3, mb: 4, borderRadius: 3 }}>
        <Typography variant="h6" gutterBottom color="primary">
          Overview
        </Typography>

        <Stack direction="row" spacing={2} alignItems="center" mb={2}>
          <Avatar sx={{ bgcolor: "primary.main" }}>
            <FitnessCenterIcon />
          </Avatar>
          <Typography variant="body1">Type: {activity.activityType}</Typography>
        </Stack>

        <Stack direction="row" spacing={2} alignItems="center" mb={2}>
          <Avatar sx={{ bgcolor: "secondary.main" }}>
            <AccessTimeIcon />
          </Avatar>
          <Typography variant="body1">
            Duration: {activity.duration} minutes
          </Typography>
        </Stack>

        <Stack direction="row" spacing={2} alignItems="center" mb={2}>
          <Avatar sx={{ bgcolor: "error.main" }}>
            <WhatshotIcon />
          </Avatar>
          <Typography variant="body1">
            Calories Burned: {activity.caloriesBurned}
          </Typography>
        </Stack>

        <Stack direction="row" spacing={2} alignItems="center">
          <Avatar sx={{ bgcolor: "info.main" }}>
            <CalendarMonthIcon />
          </Avatar>
          <Typography variant="body1">
            Date: {new Date(activity.created).toLocaleString()}
          </Typography>
        </Stack>
      </Paper>

      {/* Section: AI Recommendations */}
      {activity.recommendation && (
        <Card
          elevation={4}
          sx={{
            p: 3,
            borderLeft: "6px solid #dc004e",
            borderRadius: 3,
            backgroundColor: "#fff7f9",
            mb: 4,
          }}
        >
          <CardContent>
            <Stack direction="row" alignItems="center" spacing={1} mb={2}>
              <LightbulbIcon sx={{ color: "#dc004e" }} />
              <Typography variant="h5" fontWeight="bold">
                AI Recommendation
              </Typography>
            </Stack>

            <Typography variant="subtitle1" color="text.secondary" mb={1}>
              Analysis
            </Typography>
            <Typography paragraph>{activity.recommendation}</Typography>

            {/* Improvements */}
            {activity.improvements?.length > 0 && (
              <>
                <Divider sx={{ my: 2 }} />
                <Stack direction="row" alignItems="center" spacing={1} mb={1}>
                  <TipsAndUpdatesIcon color="success" />
                  <Typography variant="h6">Improvements</Typography>
                </Stack>
                <Box>
                  {activity.improvements.map((item, i) => (
                    <Typography
                      key={i}
                      paragraph
                      sx={{ mb: 1, pl: 2, borderLeft: "3px solid #2e7d32" }}
                    >
                      {item}
                    </Typography>
                  ))}
                </Box>
              </>
            )}

            {/* Suggestions */}
            {activity.suggestions?.length > 0 && (
              <>
                <Divider sx={{ my: 2 }} />
                <Stack direction="row" alignItems="center" spacing={1} mb={1}>
                  <TipsAndUpdatesIcon color="primary" />
                  <Typography variant="h6">Suggestions</Typography>
                </Stack>
                <Box>
                  {activity.suggestions.map((item, i) => (
                    <Typography
                      key={i}
                      paragraph
                      sx={{ mb: 1, pl: 2, borderLeft: "3px solid #1976d2" }}
                    >
                      {item}
                    </Typography>
                  ))}
                </Box>
              </>
            )}

            {/* Safety Tips */}
            {activity.safety?.length > 0 && (
              <>
                <Divider sx={{ my: 2 }} />
                <Stack direction="row" alignItems="center" spacing={1} mb={1}>
                  <SafetyCheckIcon color="warning" />
                  <Typography variant="h6">Safety Tips</Typography>
                </Stack>
                <Box>
                  {activity.safety.map((item, i) => (
                    <Typography
                      key={i}
                      paragraph
                      sx={{ mb: 1, pl: 2, borderLeft: "3px solid #ffa000" }}
                    >
                      {item}
                    </Typography>
                  ))}
                </Box>
              </>
            )}
          </CardContent>
        </Card>
      )}
    </Box>
  );
};

export default ActivityDetails;
