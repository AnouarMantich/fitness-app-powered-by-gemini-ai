import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { getActivities } from "../services/api";
import {
  Grid,
  Card,
  CardContent,
  Typography,
  Box,
  Avatar,
} from "@mui/material";

import DirectionsRunIcon from "@mui/icons-material/DirectionsRun";
import DirectionsBikeIcon from "@mui/icons-material/DirectionsBike";
import DirectionsWalkIcon from "@mui/icons-material/DirectionsWalk";

const getActivityIcon = (type) => {
  switch (type) {
    case "RUNNING":
      return <DirectionsRunIcon />;
    case "CYCLING":
      return <DirectionsBikeIcon />;
    case "WALKING":
      return <DirectionsWalkIcon />;
    default:
      return null;
  }
};

const ActivityList = () => {
  const [activities, setActivities] = useState([]);
  const navigate = useNavigate();

  const fetchActivities = async () => {
    try {
      const response = await getActivities();
      setActivities(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchActivities();
  }, []);

  return (
    <Grid container spacing={3} sx={{ mt: 2 }}>
      {activities.map((activity) => (
        <Grid item xs={12} sm={6} md={4} key={activity.id}>
          <Card
            onClick={() => navigate(`/activities/${activity.id}`)}
            sx={{
              cursor: "pointer",
              borderRadius: 3,
              boxShadow: 3,
              transition: "transform 0.2s ease, box-shadow 0.2s ease",
              "&:hover": {
                transform: "translateY(-5px)",
                boxShadow: 6,
              },
            }}
          >
            <CardContent>
              <Box display="flex" alignItems="center" mb={2}>
                <Avatar sx={{ bgcolor: "#dc004e", mr: 2 }}>
                  {getActivityIcon(activity.type)}
                </Avatar>
                <Typography variant="h6" fontWeight="bold">
                  {activity.type}
                </Typography>
              </Box>

              <Typography color="text.secondary" sx={{ mb: 1 }}>
                Duration: <strong>{activity.duration} min</strong>
              </Typography>

              <Typography color="text.secondary">
                Calories Burned: <strong>{activity.caloriesBurned}</strong>
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
};

export default ActivityList;
