# Inventory Server API

Spring Boot 기반 재고 관리 서버입니다.

---

## API 목록

### 📦 재고 (Stocks) — `/stocks`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/stocks` | 전체 재고 조회 |
| GET | `/stocks/{id}` | 재고 단건 조회 |
| GET | `/stocks/type/{typeId}` | 타입별 재고 조회 |
| GET | `/stocks/maker/{makerId}` | 제조사별 재고 조회 |
| GET | `/stocks/section/{sectionId}` | 구역별 재고 조회 |
| GET | `/stocks/search?typeId=&makerId=&specification=&sectionId=&number=` | 조건 검색 |
| POST | `/stocks/single` | 재고 단건 등록 |
| POST | `/stocks/bulk` | 재고 다건 등록 |
| PUT | `/stocks/single` | 재고 단건 수정 |
| PUT | `/stocks/bulk` | 재고 다건 수정 |
| PUT | `/stocks/location` | 재고 위치/수량 단건 수정 |
| PUT | `/stocks/locations` | 재고 위치/수량 다건 수정 |
| DELETE | `/stocks/single` | 재고 단건 삭제 |
| DELETE | `/stocks/bulk` | 재고 다건 삭제 |

---

### 📋 재고 이력 (Stock Histories) — `/stock-histories`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/stock-histories` | 전체 이력 조회 |
| GET | `/stock-histories/{id}` | 이력 단건 조회 |
| GET | `/stock-histories/category/{categoryId}` | 카테고리별 이력 조회 |
| GET | `/stock-histories/search?startDate=&endDate=&categoryId=&type=&specification=&maker=&memo=` | 조건 검색 |
| GET | `/stock-histories/released/dates` | 출고 날짜 목록 조회 |
| GET | `/stock-histories/released?date=` | 특정 날짜 출고 목록 조회 |
| POST | `/stock-histories/single` | 이력 단건 등록 |
| POST | `/stock-histories/bulk` | 이력 다건 등록 |

---

### 🗂️ 재고 이력 카테고리 (Stock History Categories) — `/stock-history-categories`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/stock-history-categories` | 전체 카테고리 조회 |

---

### 🔩 부품 (Parts) — `/parts`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/parts` | 전체 부품 조회 |
| GET | `/parts/{id}` | 부품 단건 조회 |
| GET | `/parts/type/{typeId}` | 타입별 부품 조회 |
| GET | `/parts/maker/{makerId}` | 제조사별 부품 조회 |
| GET | `/parts/search?typeId=&makerId=&specification=` | 조건 검색 |
| POST | `/parts/single` | 부품 단건 등록 |
| POST | `/parts/bulk` | 부품 다건 등록 |
| DELETE | `/parts/{id}` | 부품 단건 삭제 |
| DELETE | `/parts/bulk` | 부품 다건 삭제 |

---

### 🏭 부품 제조사 (Part Makers) — `/part-makers`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/part-makers` | 전체 제조사 조회 |
| GET | `/part-makers/{id}` | 제조사 단건 조회 |
| POST | `/part-makers` | 제조사 등록 |
| POST | `/part-makers/bulk` | 제조사 다건 등록 |
| DELETE | `/part-makers/{id}` | 제조사 단건 삭제 |
| DELETE | `/part-makers/bulk` | 제조사 다건 삭제 |

---

### 🏷️ 부품 타입 (Part Types) — `/part-types`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/part-types` | 전체 타입 조회 |
| GET | `/part-types/{id}` | 타입 단건 조회 |
| POST | `/part-types` | 타입 등록 |
| POST | `/part-types/bulk` | 타입 다건 등록 |
| DELETE | `/part-types/{id}` | 타입 단건 삭제 |
| DELETE | `/part-types/bulk` | 타입 다건 삭제 |

---

### 📐 부품 단위 (Part Units) — `/part-units`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/part-units` | 전체 단위 조회 |
| GET | `/part-units/{id}` | 단위 단건 조회 |
| POST | `/part-units` | 단위 등록 |
| POST | `/part-units/bulk` | 단위 다건 등록 |
| DELETE | `/part-units/{id}` | 단위 단건 삭제 |
| DELETE | `/part-units/bulk` | 단위 다건 삭제 |

---

### 📍 위치 (Locations) — `/locations`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/locations` | 전체 위치 조회 |
| GET | `/locations/{id}` | 위치 단건 조회 |
| GET | `/locations/section/{sectionId}` | 구역별 위치 조회 |
| GET | `/locations/search?sectionId=&number=` | 조건 검색 |
| POST | `/locations/single` | 위치 단건 등록 |
| POST | `/locations/bulk` | 위치 다건 등록 |
| DELETE | `/locations/{id}` | 위치 단건 삭제 |
| DELETE | `/locations/bulk` | 위치 다건 삭제 |

---

### 🗃️ 위치 구역 (Location Sections) — `/location-sections`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/location-sections` | 전체 구역 조회 |
| GET | `/location-sections/{id}` | 구역 단건 조회 |
| POST | `/location-sections` | 구역 등록 |
| POST | `/location-sections/bulk` | 구역 다건 등록 |
| DELETE | `/location-sections/{id}` | 구역 단건 삭제 |
| DELETE | `/location-sections/bulk` | 구역 다건 삭제 |

---

### 👤 담당자 (Persons) — `/persons`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/persons` | 전체 담당자 조회 |
| GET | `/persons/{id}` | 담당자 단건 조회 |
| POST | `/persons` | 담당자 등록 |
| POST | `/persons/bulk` | 담당자 다건 등록 |
| DELETE | `/persons/{id}` | 담당자 단건 삭제 |
| DELETE | `/persons/bulk` | 담당자 다건 삭제 |

---

### 💾 백업 (Backup) — `/backup`

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/backup/dump` | DB 백업 실행 |
| POST | `/backup/restore` | DB 복원 실행 |
| GET | `/backup/last-backup` | 마지막 백업 시각 조회 |


# Inventory Server API

Spring Boot 기반 재고 관리 서버입니다.

---

## API 목록

### 📦 재고 (Stocks) — `/stocks`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/stocks` | 전체 재고 조회 |
| GET | `/stocks/{id}` | 재고 단건 조회 |
| GET | `/stocks/type/{typeId}` | 타입별 재고 조회 |
| GET | `/stocks/maker/{makerId}` | 제조사별 재고 조회 |
| GET | `/stocks/section/{sectionId}` | 구역별 재고 조회 |
| GET | `/stocks/search?typeId=&makerId=&specification=&sectionId=&number=` | 조건 검색 |
| POST | `/stocks/single` | 재고 단건 등록 |
| POST | `/stocks/bulk` | 재고 다건 등록 |
| PUT | `/stocks/single` | 재고 단건 수정 |
| PUT | `/stocks/bulk` | 재고 다건 수정 |
| PUT | `/stocks/location` | 재고 위치/수량 단건 수정 |
| PUT | `/stocks/locations` | 재고 위치/수량 다건 수정 |
| DELETE | `/stocks/single` | 재고 단건 삭제 |
| DELETE | `/stocks/bulk` | 재고 다건 삭제 |

---

### 📋 재고 이력 (Stock Histories) — `/stock-histories`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/stock-histories` | 전체 이력 조회 |
| GET | `/stock-histories/{id}` | 이력 단건 조회 |
| GET | `/stock-histories/category/{categoryId}` | 카테고리별 이력 조회 |
| GET | `/stock-histories/search?startDate=&endDate=&categoryId=&type=&specification=&maker=&memo=` | 조건 검색 |
| GET | `/stock-histories/released/dates` | 출고 날짜 목록 조회 |
| GET | `/stock-histories/released?date=` | 특정 날짜 출고 목록 조회 |
| POST | `/stock-histories/single` | 이력 단건 등록 |
| POST | `/stock-histories/bulk` | 이력 다건 등록 |

---

### 🗂️ 재고 이력 카테고리 (Stock History Categories) — `/stock-history-categories`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/stock-history-categories` | 전체 카테고리 조회 |

---

### 🔩 부품 (Parts) — `/parts`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/parts` | 전체 부품 조회 |
| GET | `/parts/{id}` | 부품 단건 조회 |
| GET | `/parts/type/{typeId}` | 타입별 부품 조회 |
| GET | `/parts/maker/{makerId}` | 제조사별 부품 조회 |
| GET | `/parts/search?typeId=&makerId=&specification=` | 조건 검색 |
| POST | `/parts/single` | 부품 단건 등록 |
| POST | `/parts/bulk` | 부품 다건 등록 |
| DELETE | `/parts/{id}` | 부품 단건 삭제 |
| DELETE | `/parts/bulk` | 부품 다건 삭제 |

---

### 🏭 부품 제조사 (Part Makers) — `/part-makers`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/part-makers` | 전체 제조사 조회 |
| GET | `/part-makers/{id}` | 제조사 단건 조회 |
| POST | `/part-makers` | 제조사 등록 |
| POST | `/part-makers/bulk` | 제조사 다건 등록 |
| DELETE | `/part-makers/{id}` | 제조사 단건 삭제 |
| DELETE | `/part-makers/bulk` | 제조사 다건 삭제 |

---

### 🏷️ 부품 타입 (Part Types) — `/part-types`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/part-types` | 전체 타입 조회 |
| GET | `/part-types/{id}` | 타입 단건 조회 |
| POST | `/part-types` | 타입 등록 |
| POST | `/part-types/bulk` | 타입 다건 등록 |
| DELETE | `/part-types/{id}` | 타입 단건 삭제 |
| DELETE | `/part-types/bulk` | 타입 다건 삭제 |

---

### 📐 부품 단위 (Part Units) — `/part-units`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/part-units` | 전체 단위 조회 |
| GET | `/part-units/{id}` | 단위 단건 조회 |
| POST | `/part-units` | 단위 등록 |
| POST | `/part-units/bulk` | 단위 다건 등록 |
| DELETE | `/part-units/{id}` | 단위 단건 삭제 |
| DELETE | `/part-units/bulk` | 단위 다건 삭제 |

---

### 📍 위치 (Locations) — `/locations`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/locations` | 전체 위치 조회 |
| GET | `/locations/{id}` | 위치 단건 조회 |
| GET | `/locations/section/{sectionId}` | 구역별 위치 조회 |
| GET | `/locations/search?sectionId=&number=` | 조건 검색 |
| POST | `/locations/single` | 위치 단건 등록 |
| POST | `/locations/bulk` | 위치 다건 등록 |
| DELETE | `/locations/{id}` | 위치 단건 삭제 |
| DELETE | `/locations/bulk` | 위치 다건 삭제 |

---

### 🗃️ 위치 구역 (Location Sections) — `/location-sections`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/location-sections` | 전체 구역 조회 |
| GET | `/location-sections/{id}` | 구역 단건 조회 |
| POST | `/location-sections` | 구역 등록 |
| POST | `/location-sections/bulk` | 구역 다건 등록 |
| DELETE | `/location-sections/{id}` | 구역 단건 삭제 |
| DELETE | `/location-sections/bulk` | 구역 다건 삭제 |

---

### 👤 담당자 (Persons) — `/persons`

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/persons` | 전체 담당자 조회 |
| GET | `/persons/{id}` | 담당자 단건 조회 |
| POST | `/persons` | 담당자 등록 |
| POST | `/persons/bulk` | 담당자 다건 등록 |
| DELETE | `/persons/{id}` | 담당자 단건 삭제 |
| DELETE | `/persons/bulk` | 담당자 다건 삭제 |

---

### 💾 백업 (Backup) — `/backup`

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/backup/dump` | DB 백업 실행 |
| POST | `/backup/restore` | DB 복원 실행 |
| GET | `/backup/last-backup` | 마지막 백업 시각 조회 |