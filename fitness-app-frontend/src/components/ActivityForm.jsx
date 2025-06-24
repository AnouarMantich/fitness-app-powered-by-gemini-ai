import {
  Box,
  Button,
  Card,
  CardContent,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
  Typography,
} from "@mui/material";
import React, { useState } from "react";
import { addActivity } from "../services/api";
import FitnessCenterIcon from "@mui/icons-material/FitnessCenter";

const ActivityForm = () => {
  const [activity, setActivity] = useState({
    type: "RUNNING",
    duration: "",
    caloriesBurned: "",
    additionalMetrics: {},
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("form submitted");
    try {
      await addActivity(activity);
      setActivity({
        type: "RUNNING",
        duration: "",
        caloriesBurned: "",
        additionalMetrics: {},
      });
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Card
      sx={{ maxWidth: 500, mx: "auto", mt: 5, boxShadow: 4, borderRadius: 3 }}
    >
      <CardContent>
        <Box display="flex" alignItems="center" mb={3}>
          <FitnessCenterIcon color="primary" sx={{ fontSize: 32, mr: 1 }} />
          <Typography variant="h5" fontWeight="bold">
            Log New Activity
          </Typography>
        </Box>

        <Box component="form" onSubmit={handleSubmit}>
          <FormControl fullWidth sx={{ mb: 3 }}>
            <InputLabel>Activity Type</InputLabel>
            <Select
              value={activity.type}
              label="Activity Type"
              onChange={(e) =>
                setActivity({ ...activity, type: e.target.value })
              }
              sx={{ borderRadius: 2 }}
            >
              <MenuItem value="RUNNING">Running</MenuItem>
              <MenuItem value="CYCLING">Cycling</MenuItem>
              <MenuItem value="WALKING">Walking</MenuItem>
            </Select>
          </FormControl>

          <TextField
            fullWidth
            label="Duration (minutes)"
            type="number"
            variant="outlined"
            sx={{ mb: 3 }}
            value={activity.duration}
            onChange={(e) =>
              setActivity({ ...activity, duration: e.target.value })
            }
            InputProps={{ sx: { borderRadius: 2 } }}
          />

          <TextField
            fullWidth
            label="Calories Burned"
            type="number"
            variant="outlined"
            sx={{ mb: 3 }}
            value={activity.caloriesBurned}
            onChange={(e) =>
              setActivity({ ...activity, caloriesBurned: e.target.value })
            }
            InputProps={{ sx: { borderRadius: 2 } }}
          />

          <Button
            type="submit"
            variant="contained"
            fullWidth
            sx={{
              backgroundColor: "#dc004e",
              color: "#fff",
              paddingY: 1.2,
              fontWeight: "bold",
              borderRadius: 2,
              textTransform: "none",
              "&:hover": {
                backgroundColor: "#b3003c",
              },
            }}
          >
            Add Activity
          </Button>
        </Box>
      </CardContent>
    </Card>
  );
};

export default ActivityForm;
