let stompClient = null;
let senderuser = { id: null, username: null };
let currentReceiver = { id: null, username: null };
let currentSubscription = null;

// 🚀 STEP 1 — Fetch session user on load
window.addEventListener("DOMContentLoaded", () => {
  fetch("http://localhost:8080/currentUser")
    .then((res) => res.json())
    .then((user) => {
      if (user && user.name) {
        // Store user in localStorage
        localStorage.setItem("loggedInUser", JSON.stringify(user));
        senderuser["id"] = user.id;
        senderuser["username"] = user.name;

        // Auto connect to chat
        connect();
      } else {
        // Session expired — redirect
        window.location.href = "/login";
      }
    })
    .catch((err) => {
      console.error("Error fetching session user:", err);
      window.location.href = "/login";
    });
});

// Connect user to chat automatically
function connect() {
  const storedUser = JSON.parse(localStorage.getItem("loggedInUser"));
  if (!storedUser) {
    return (window.location.href = "/login");
  }

  username = storedUser.name;

  stompClient = new StompJs.Client({
    brokerURL: "ws://localhost:8080/chat",

    onConnect: () => {
      showSystemMessage("✅ Connected as " + username);
      document.getElementById("header").innerText = "Chat - " + username;
      getAlumni();
    },
  });

  stompClient.activate();
}

// 🚀 STEP 3 — Send message
function sendMessage() {
  const text = document.getElementById("message").value.trim();
  if (!currentReceiver) return alert("Select a user first!");
  if (!text) return;

  const msg = {
    sender: { id: senderuser.id, name: senderuser.username },

    receiver: {
      id: currentReceiver.id,
      name: currentReceiver.username,
    },
    content: text,
  };

  stompClient.publish({
    destination: "/app/private-message",
    body: JSON.stringify(msg),
  });

  // showMessage("You", text, "you");
  document.getElementById("message").value = "";
}

// 🚀 STEP 4 — Display message in chat
function showMessage(sender, text, type) {
  const div = document.createElement("div");
  div.classList.add("message", type);
  div.textContent = sender + ": " + text;
  document.getElementById("chatBox").appendChild(div);
  div.scrollIntoView({ behavior: "smooth" });
}

function showSystemMessage(text) {
  const div = document.createElement("div");
  div.style.textAlign = "center";
  div.style.color = "#666";
  div.textContent = text;
  document.getElementById("chatBox").appendChild(div);
}

// 🚀 STEP 5 — Load all alumni users
function getAlumni() {
  fetch("http://localhost:8080/getAllAlumni")
    .then((res) => res.json())
    .then((data) => {
      const list = document.getElementById("user-list");
      list.innerHTML = "";

      data.forEach((a) => {
        if (a.name === username) return; // don't show yourself

        const card = document.createElement("div");
        card.classList.add("user-card");
        card.textContent = a.name;
        card.dataset.name = a.name;

        card.onclick = () => {
          document
            .querySelectorAll(".user-card")
            .forEach((c) => c.classList.remove("active"));
          card.classList.add("active");

          currentReceiver["id"] = a.id;
          currentReceiver["username"] = a.name;
          document.getElementById("header").innerText =
            "Chat with " + currentReceiver["username"];
          showSystemMessage("Chatting with: " + a.name);
          console.log(currentReceiver);

          if (currentSubscription) {
            currentSubscription.unsubscribe();
          }
          let roomID =
            Math.max(senderuser["id"], currentReceiver["id"]) +
            "_" +
            Math.min(senderuser["id"], currentReceiver["id"]);

          console.log(roomID);

          currentSubscription = stompClient.subscribe(
            "/user/" + roomID + "/private",
            (message) => {
              const msg = JSON.parse(message.body);
              if (msg.sender.id === senderuser.id) {
                showMessage("You", msg.content, "you"); // right side
              } else {
                showMessage(msg.sender.name, msg.content, "other"); // left side
              }
            }
          );
        };

        list.appendChild(card);
      });
    });
}
