const express = require("express");

const app = express();
app.use(express.json());

const reviewsByStation = new Map();
const occupancyByStation = new Map();
const scheduleByStation = new Map();

app.get("/health", (req, res) => {
  res.json({ status: "ok" });
});

app.post("/reviews", (req, res) => {
  const { stationId, rating, comment } = req.body || {};
  if (!stationId || typeof rating !== "number") {
    return res.status(400).json({ error: "stationId and rating are required" });
  }
  const entry = {
    rating,
    comment: comment || "",
    createdAt: new Date().toISOString(),
  };
  const existing = reviewsByStation.get(stationId) || [];
  existing.push(entry);
  reviewsByStation.set(stationId, existing);
  res.status(201).json({ stationId, saved: true });
});

app.get("/reviews/:stationId", (req, res) => {
  const { stationId } = req.params;
  const reviews = reviewsByStation.get(stationId) || [];
  const averageRating = reviews.length
    ? reviews.reduce((sum, review) => sum + review.rating, 0) / reviews.length
    : null;
  res.json({ stationId, count: reviews.length, averageRating, reviews });
});

app.post("/occupancy", (req, res) => {
  const { stationId, level } = req.body || {};
  if (!stationId || typeof level !== "string") {
    return res.status(400).json({ error: "stationId and level are required" });
  }
  const payload = { stationId, level, updatedAt: new Date().toISOString() };
  occupancyByStation.set(stationId, payload);
  res.status(201).json(payload);
});

app.get("/occupancy/:stationId", (req, res) => {
  const { stationId } = req.params;
  const payload = occupancyByStation.get(stationId);
  if (!payload) {
    return res.status(404).json({ error: "No occupancy data" });
  }
  res.json(payload);
});

app.post("/schedule", (req, res) => {
  const { stationId, schedule } = req.body || {};
  if (!stationId || !schedule) {
    return res.status(400).json({ error: "stationId and schedule are required" });
  }
  const payload = { stationId, schedule, updatedAt: new Date().toISOString() };
  scheduleByStation.set(stationId, payload);
  res.status(201).json(payload);
});

app.get("/schedule/:stationId", (req, res) => {
  const { stationId } = req.params;
  const payload = scheduleByStation.get(stationId);
  if (!payload) {
    return res.status(404).json({ error: "No schedule data" });
  }
  res.json(payload);
});

app.post("/auth/token", (req, res) => {
  const { userId } = req.body || {};
  if (!userId) {
    return res.status(400).json({ error: "userId is required" });
  }
  res.json({ token: `demo-token-${userId}`, expiresIn: 3600 });
});

app.get("/aggregate/:stationId", (req, res) => {
  const { stationId } = req.params;
  const reviews = reviewsByStation.get(stationId) || [];
  const occupancy = occupancyByStation.get(stationId) || null;
  const schedule = scheduleByStation.get(stationId) || null;
  const averageRating = reviews.length
    ? reviews.reduce((sum, review) => sum + review.rating, 0) / reviews.length
    : null;
  res.json({
    stationId,
    reviewCount: reviews.length,
    averageRating,
    occupancy,
    schedule,
  });
});

const port = process.env.PORT || 4000;
app.listen(port, () => {
  console.log(`Petro Papi backend listening on port ${port}`);
});
