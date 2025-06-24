import React from "react";
import ActivityList from "./ActivityList";

import { Box } from "@mui/material";
import ActivityForm from "./ActivityForm";

const ActivitiesPage = () => {
  return (
    <Box component="section" sx={{ p: 2, border: "1px dashed grey" }}>
      <ActivityForm
      // onActivityAdded={() => window.location.reload()}
      />
      <ActivityList />
    </Box>
  );
};

export default ActivitiesPage;
