## REST API in JAVA
- Call a public REST endpoint
- Extract data from JSON
- Handle pagination (page, total_pages)
- Filter records
- Compute result (count, sum, list, etc.)

### Calling Endpoint
```java
HttpClient client = HttpClient.newHttpClient();

HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build();

HttpResponse<String> response =
        client.send(request, HttpResponse.BodyHandlers.ofString());
```
### JSON parsing
*JSONObject*
From the library org.json.JSONObject
A JSONObject = JSON dictionary (key–value pairs)
Equivalent to: Map<String, Object>
example:
```JSON
{
  "name": "Messi",
  "age": 36,
  "team": "Inter Miami"
}
```

*JSONArray*
From the library org.json.JSONArray
A JSONArray = JSON list / array
List<Object>
Example:
```JSON
[
  { "name": "Messi" },
  { "name": "Ronaldo" }
]
```
Common API response contains:
```JSON
{
  "page": 1,
  "total_pages": 2,
  "data": [
    { "title": "Movie A" },
    { "title": "Movie B" }
  ]
}
```
code:
```Java
JSONObject response = new JSONObject(body);

int totalPages = response.getInt("total_pages");

JSONArray data = response.getJSONArray("data");

List<String> titles = new ArrayList<>();

for (int i = 0; i < data.length(); i++) {
    JSONObject movie = data.getJSONObject(i);
    titles.add(movie.getString("title"));
}
```
Flow:
```code
JSONObject (whole response)
   ↓
JSONArray ("data")
   ↓
JSONObject (each movie)
   ↓
List<String> (store final result)
```

### Pagination
General Template:
```Java
int page = 1;
int totalPages = 1;

while (page <= totalPages) {

    String url = baseUrl + "?page=" + page;
    // call API

    JSONObject obj = new JSONObject(response.body());

    totalPages = obj.getInt("total_pages");

    JSONArray data = obj.getJSONArray("data");

    // process data

    page++;
}
```

## Common HackerRank question patterns

### Return sorted list
Collections.sort(list);

### Count matches
if (team.equals("Barcelona")) count++;

### Average
double avg = sum / (double) count;

### Unique values
Set<String> set = new HashSet<>();
