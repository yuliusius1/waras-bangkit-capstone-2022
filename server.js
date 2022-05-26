const express = require("express")

const app = express();

app.use(express.json());

// Import routes
const authRouter = require("./routes/auth");
const handlerRouter = require("./routes/handler");

// Setup all the routes
app.use("/api/handler", handlerRouter);
app.use("/api/auth", authRouter);

const port = process.env.port || 8080;
app.listen(port, ()=>{
	console.log(`Waras REST API listening on port ${port}`);
});

app.get("/", async (req, res)=> {
	res.json({ status: "Waras APP READY!!"})
});