const baseUrl = "http://localhost:8083"; // API Gateway URL

function showSection(id) {
  document.querySelectorAll(".section").forEach(sec => sec.classList.add("hidden"));
  document.getElementById(id).classList.remove("hidden");
}

function backToOptions() {
  document.querySelectorAll(".section").forEach(sec => sec.classList.add("hidden"));
}

// ---------------- CREATE CARD ----------------
document.getElementById("cardForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const name = document.getElementById("name").value;
  const email = document.getElementById("email").value;
  const phoneNo = document.getElementById("phoneNo").value;
  const balance = document.getElementById("balance").value;

  const cardData = { name, email, phoneNo, balance };

  try {
    const response = await fetch(`${baseUrl}/card/create`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(cardData)
    });
    const result = await response.text();
    document.getElementById("responseMessage").textContent = "✅ Card Created: " + result;
    e.target.reset();
  } catch {
    document.getElementById("responseMessage").textContent = "❌ Error creating card.";
  }
});

// ---------------- VIEW ALL CARDS ----------------
document.getElementById("viewAllBtn").addEventListener("click", async () => {
  const username = prompt("Enter Username:");
  const password = prompt("Enter Password:");

  if (username === "abc" && password === "123") {
    showSection("allCardsSection");
    try {
      const res = await fetch(`${baseUrl}/card/getAll`);
      const cards = await res.json();
      const container = document.getElementById("cardsContainer");
      container.innerHTML = "";

      cards.forEach(card => {
        container.innerHTML += `
          <div class="card">
            <div class="card-inner">
              <div class="card-front">
                <h3>${card.name}</h3>
                <p>${card.email}</p>
                <p>Balance: ₹${card.balance}</p>
              </div>
              <div class="card-back">
                <p>ID: ${card.id}</p>
                <p>Phone: ${card.phoneNo}</p>
                <p>Expiry: ${card.expiry || "MM/YY"}</p>
              </div>
            </div>
          </div>`;
      });
    } catch {
      alert("Error fetching cards");
    }
  } else {
    alert("❌ Invalid credentials!");
  }
});

// ---------------- GET CARD BY ID ----------------
async function getCardDetails() {
  const id = document.getElementById("cardId").value;
  if (!id) return alert("Enter Card ID");
  try {
    const res = await fetch(`${baseUrl}/card/get/${id}`);
    if (!res.ok) throw new Error();
    const card = await res.json();
    document.getElementById("cardDetails").innerHTML = `
      <div class="card">
        <div class="card-inner">
          <div class="card-front">
            <h3>${card.name}</h3>
            <p>${card.email}</p>
            <p>Balance: ₹${card.balance}</p>
          </div>
          <div class="card-back">
            <p>ID: ${card.id}</p>
            <p>Phone: ${card.phoneNo}</p>
            <p>Expiry: ${card.expiry || "MM/YY"}</p>
          </div>
        </div>
      </div>`;
  } catch {
    alert("Card not found!");
  }
}

// ---------------- TRANSACTION HISTORY ----------------
const fetchBtn = document.getElementById("fetchBtn");
const userIdInput = document.getElementById("userId");
const totalCountEl = document.getElementById("totalCount");
const successCountEl = document.getElementById("successCount");
const failedCountEl = document.getElementById("failedCount");
const tableContainer = document.getElementById("tableContainer");
const ctx = document.getElementById("transactionChart").getContext("2d");
let chart;

fetchBtn.addEventListener("click", async () => {
  const userId = userIdInput.value.trim();
  if (!userId) return alert("Enter Card ID");

  try {
    const res = await fetch(`${baseUrl}/transfer/get/history/${userId}`);
    const data = await res.json();

    updateStats(data);
    renderTable(data);
    renderChart(data);
  } catch (err) {
    alert("Error fetching transaction data");
    console.error(err);
  }
});

function updateStats(transactions) {
  totalCountEl.textContent = transactions.length;
  successCountEl.textContent = transactions.filter(t => t.status === "SUCCESS").length;
  failedCountEl.textContent = transactions.filter(t => t.status === "FAILED").length;
}

function renderTable(transactions) {
  if (!transactions.length) {
    tableContainer.innerHTML = "<p>No transactions found.</p>";
    return;
  }

  const rows = transactions.map(t => `
    <tr>
      <td>${t.id}</td>
      <td>${t.senderId}</td>
      <td>${t.receiverId}</td>
      <td>₹${t.transferredAmount}</td>
      <td style="color:${t.status === "SUCCESS" ? "#4caf50" : "#f44336"};">${t.status}</td>
      <td>${new Date(t.timestamp).toLocaleString()}</td>
    </tr>
  `).join("");

  tableContainer.innerHTML = `
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Sender</th>
          <th>Receiver</th>
          <th>Amount</th>
          <th>Status</th>
          <th>Date</th>
        </tr>
      </thead>
      <tbody>${rows}</tbody>
    </table>
  `;
}

function renderChart(transactions) {
  const labels = transactions.map(t => new Date(t.timestamp).toLocaleDateString());
  const values = transactions.map(t => t.transferredAmount);

  if (chart) chart.destroy();
  chart = new Chart(ctx, {
    type: "bar",
    data: {
      labels,
      datasets: [{
        label: "Transaction Amount",
        data: values,
        backgroundColor: "rgba(0, 188, 212, 0.5)",
        borderColor: "#00bcd4",
        borderWidth: 2,
        hoverBackgroundColor: "#00acc1",
      }]
    },
    options: {
      animation: { duration: 1500, easing: "easeOutBounce" },
      scales: {
        y: { beginAtZero: true, grid: { color: "rgba(255,255,255,0.1)" }, ticks: { color: "#fff" } },
        x: { ticks: { color: "#fff" }, grid: { color: "rgba(255,255,255,0.1)" } }
      },
      plugins: { legend: { labels: { color: "#fff" } } }
    }
  });
}

// ---------------- TRANSFER FUNDS ----------------
document.getElementById("transferForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const senderId = document.getElementById("senderId").value;
  const receiverId = document.getElementById("receiverId").value;
  const amount = document.getElementById("amount").value;

  try {
    const res = await fetch(`${baseUrl}/transfer/perform`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ senderId, receiverId, amount })
    });
    const msg = await res.text();
    document.getElementById("transferMessage").textContent = "✅ " + msg;
    e.target.reset();
  } catch {
    document.getElementById("transferMessage").textContent = "❌ Transfer failed!";
  }
});
