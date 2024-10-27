# springfield-api
Java + Spring Boot = A web API that returns info on characters from The Simpsons.

## Try it out!
Try the endpoints yourself in the live demo!

## Available Endpoints
`GET` `/api/characters`
- Returns an array of all characters in the SQL DB.

`GET` `/api/characters/{id}`
- Returns info on a Simpsons character given their GUID in the table.

## Schema
```
SimpsonsCharacter = {
  UUID guid,
  String firstName,
  String lastName,
  String occupation,
  Int age,
  String voicedBy
}
```
