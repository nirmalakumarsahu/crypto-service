# crypto-service

---

## 📚 **Cryptographic Algorithms Classification**

### 🔐 **1. Symmetric Encryption Algorithms**

Use the **same key** for encryption and decryption.

| Name       | Algorithm         | Key Size | Mode/Padding | Notes                                       |
| ---------- | ----------------- | -------- | ------------ | ------------------------------------------- |
| **AES128** | AES/GCM/NoPadding | 128 bits | GCM mode     | Fast, secure for data at rest or in transit |
| **AES256** | AES/GCM/NoPadding | 256 bits | GCM mode     | Stronger key, slightly slower               |

📘 **Category:** Symmetric (Block Cipher)

### 🔑 **2. Asymmetric Encryption Algorithms**

Use a **public key** to encrypt and a **private key** to decrypt.

| Name        | Algorithm                             | Key Size  | Mode/Padding | Notes                                  |
| ----------- | ------------------------------------- | --------- | ------------ | -------------------------------------- |
| **RSA2048** | RSA/ECB/OAEPWithSHA-256AndMGF1Padding | 2048 bits | OAEP padding | Common for key exchange, not bulk data |
| **RSA4096** | RSA/ECB/OAEPWithSHA-256AndMGF1Padding | 4096 bits | OAEP padding | Stronger, but slower                   |

📘 **Category:** Asymmetric (Public Key Cryptography)

### 🧮 **3. Hash Algorithms**

Produce a **fixed-length digest** from data — no key, irreversible.

| Name       | Algorithm | Output   | Notes                                    |
| ---------- | --------- | -------- | ---------------------------------------- |
| **SHA256** | SHA-256   | 256 bits | Common for digital signatures, integrity |
| **SHA512** | SHA-512   | 512 bits | Stronger, used in modern PKI systems     |

📘 **Category:** Hash Functions (One-way)

### 🧾 **4. Message Authentication Codes (HMAC)**

Combine a **hash function + secret key** for message integrity and authentication.

| Name           | Algorithm  | Output   | Notes                         |
| -------------- | ---------- | -------- | ----------------------------- |
| **HMACSHA256** | HmacSHA256 | 256 bits | Common in JWTs, APIs          |
| **HMACSHA512** | HmacSHA512 | 512 bits | Used in high-security systems |

📘 **Category:** Message Authentication Code (MAC)

### ✅ **Summary Classification**

| Algorithm              | Category              | Key Type                |
| ---------------------- | --------------------- | ----------------------- |
| AES128, AES256         | Symmetric Encryption  | Secret key              |
| RSA2048, RSA4096       | Asymmetric Encryption | Public/Private key pair |
| SHA256, SHA512         | Hash Function         | No key                  |
| HMACSHA256, HMACSHA512 | HMAC (Keyed Hash)     | Secret key              |

---


## 🧩 OVERVIEW: What Each Algorithm Can and Cannot Do

| Algorithm                 | Type       | Supports Encryption/Decryption? | Supports Files? | Notes                              |
| ------------------------- | ---------- | ------------------------------- | --------------- | ---------------------------------- |
| AES-128 / AES-256         | Symmetric  | ✅ Yes                           | ✅ Yes           | Fast, secure for text/files        |
| RSA-2048 / RSA-4096       | Asymmetric | ✅ Yes, but not for large files  | ⚠️ Partial      | Usually for keys or small data     |
| SHA-256 / SHA-512         | Hash       | ❌ No (one-way only)             | ✅ Yes           | For integrity, not encryption      |
| HMAC-SHA256 / HMAC-SHA512 | MAC        | ✅ Verifies integrity            | ✅ Yes           | Used for authenticity, not secrecy |



### 🧠 Summary of Use Cases

| Algorithm             | Use Case                 | Suitable For                          |
| --------------------- | ------------------------ | ------------------------------------- |
| **AES (128/256)**     | Data encryption          | Text, files, APIs                     |
| **RSA (2048/4096)**   | Key exchange, small data | Keys, credentials                     |
| **SHA (256/512)**     | Integrity verification   | Passwords, file integrity             |
| **HMAC (SHA256/512)** | Message authentication   | APIs, tokens, file integrity with key |

---
