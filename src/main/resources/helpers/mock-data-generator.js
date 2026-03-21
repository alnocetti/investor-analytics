// ===============================
// Helpers
// ===============================
function randomFromArray(arr) {
  return arr[Math.floor(Math.random() * arr.length)];
}

function randomFloat(min, max) {
  return Math.random() * (max - min) + min;
}

function randomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

function generateWeightedAllocations(n, totalSize) {
  let weights = [];
  for (let i = 0; i < n; i++) {
    weights.push(1 / (i + 1));
  }

  const sumWeights = weights.reduce((a, b) => a + b, 0);

  return weights.map(w => (w / sumWeights) * totalSize);
}

const geoMap = [
  { country: "United States", region: "North America" },
  { country: "Canada", region: "North America" },
  { country: "UK", region: "Europe" },
  { country: "Germany", region: "Europe" },
  { country: "France", region: "Europe" },
  { country: "Brazil", region: "South America" },
  { country: "Argentina", region: "South America" }
];

const sectorMap = [
  { sector: "Technology", subSectors: ["SaaS", "AI", "Cybersecurity"] },
  { sector: "Healthcare", subSectors: ["Biotech", "Pharma"] },
  { sector: "Finance", subSectors: ["Fintech", "Payments"] },
  { sector: "Energy", subSectors: ["Oil & Gas", "Renewables"] }
];

const investorTypes = [
  "Venture Fund",
  "Hedge Fund",
  "Pension Fund",
  "Private Equity"
];

// ===============================
// clean up
// ===============================
db.investors.deleteMany({});
db.issuers.deleteMany({});
db.deals.deleteMany({});

// ===============================
// Investors
// ===============================
const investors = [];

for (let i = 0; i < 100; i++) {
  investors.push({
    _id: new ObjectId(),
    name: `Investor ${i + 1}`,
    type: randomFromArray(investorTypes)
  });
}

db.investors.insertMany(investors);

// ===============================
// Issuers
// ===============================
const issuers = [];

for (let i = 0; i < 100; i++) {
  issuers.push({
    _id: new ObjectId(),
    name: `Issuer ${i + 1}`,
  });
}

db.issuers.insertMany(issuers);

// ===============================
// Deals (🔥 SIN DBRef)
// ===============================
const BATCH_SIZE = 500;
let batch = [];

const startDate = new Date("2023-01-01");
const endDate = new Date("2025-12-31");

for (let i = 0; i < 3000; i++) {
  const issuer = randomFromArray(issuers);

  const investorCount = randomInt(20, 50);

  const weightedInvestors = investors
    .slice()
    .sort((a, b) => {
      const weight = t => (t === "Tier 1" ? 3 : t === "Tier 2" ? 2 : 1);
      return Math.random() * weight(b.tier) - Math.random() * weight(a.tier);
    })
    .slice(0, investorCount);

  const dealSize = randomFloat(2_000_000, 20_000_000);
  const allocations = generateWeightedAllocations(investorCount, dealSize);

  let investorDealAnalytics = [];

  for (let j = 0; j < investorCount; j++) {
    const inv = weightedInvestors[j];

    const allocation = allocations[j];
    const demand = allocation * randomFloat(1.05, 1.6);

    investorDealAnalytics.push({
      investor: {
        _id: inv._id,
        name: inv.name,
        type: inv.type
      },
      allocation,
      demand,
      allocationRank: 0,
      demandRank: 0
    });
  }

  investorDealAnalytics
    .sort((a, b) => b.allocation - a.allocation)
    .forEach((x, idx) => (x.allocationRank = idx + 1));

  investorDealAnalytics
    .slice()
    .sort((a, b) => b.demand - a.demand)
    .forEach((x, idx) => (x.demandRank = idx + 1));

  const totalAllocation = investorDealAnalytics.reduce((s, x) => s + x.allocation, 0);
  const totalDemand = investorDealAnalytics.reduce((s, x) => s + x.demand, 0);

  const pricingDate = new Date(
    startDate.getTime() +
      Math.random() * (endDate.getTime() - startDate.getTime())
  );

  const sectorObj = randomFromArray(sectorMap);
  const geo = randomFromArray(geoMap);

  const deal = {
    _id: new ObjectId(),
    pricingDate,
    type: "Equity",
    sector: sectorObj.sector,
    subSector: randomFromArray(sectorObj.subSectors),
    country: geo.country,
    region: geo.region,
    size: dealSize,
    totalAllocation,
    totalDemand,

    // 🔥 EMBEDDED issuer
    issuer: {
      _id: issuer._id,
      name: issuer.name
    },

    investorDealAnalytics
  };

  batch.push(deal);

  if (batch.length === BATCH_SIZE) {
    db.deals.insertMany(batch);
    batch = [];
  }
}

if (batch.length) db.deals.insertMany(batch);

// ===============================
// Indexes (🔥 actualizados)
// ===============================
db.deals.createIndex({ pricingDate: 1 });
db.deals.createIndex({ "issuer.id": 1 });
db.deals.createIndex({ "investorDealAnalytics.investor.id": 1 });

// ===============================
// Result
// ===============================
print("✅ GENERATED DATA:");
print(`Investors: ${db.investors.countDocuments()}`);
print(`Issuers: ${db.issuers.countDocuments()}`);
print(`Deals: ${db.deals.countDocuments()}`);