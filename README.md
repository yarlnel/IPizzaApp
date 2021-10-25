# IPizzaApp 

__Api__ приложения:
# i-Pizza API

This api will help you to send and receive data from our REST API.


## General
**Base URL**: `https://i-pizza.herokuapp.com/`
**Authorization**: Not required
**Headers**: Not required
**Error body example**: `400 - BAD_REQUEST`
 ```
 {

"timestamp": "2021-10-23T19:06:25.150+0000",

"status": 400,

"error": "Bad Request",

"message": "Invalid JSON input: Cannot deserialize instance of `java.util.ArrayList<com.tamimattafi.backend.demo.api.controllers.pizza.PizzaEntity>` out of START_OBJECT token; nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize instance of `java.util.ArrayList<com.tamimattafi.backend.demo.api.controllers.pizza.PizzaEntity>` out of START_OBJECT token\n at [Source: (PushbackInputStream); line: 1, column: 1]",

"path": "/pizza/order"

}
```


**Using other methods `(POST/GET/PUT/PATCH/DELETE)` than the ones described in these docs will result in a `404 NOT_FOUND` error**

**Sending other bodies than the ones described in these docs will result in a `400 BAD_REQUEST` error**

## Endpoints

### Get a list of the available Pizzas
**Path**: `/pizza`
**Method**: `GET`
**Request Body**: N/A
**Parameters**: N/A
**Response**: `OK - 200`
```
[
   {
      "id":1,
      "name":"Margarita",
      "price":799,
      "imageUrls":[
         "https://static.1000.menu/img/content-v2/ef/27/10853/picca-margarita-v-domashnix-usloviyax_1608783820_4_max.jpg"
      ],
      "description":"Pizza Margherita (more commonly known in English as Margherita pizza) is a typical Neapolitan pizza, made with San Marzano tomatoes, mozzarella cheese, fresh basil, salt, and extra-virgin olive oil."
   },
   {
      "id":2,
      "name":"Detroit",
      "price":999,
      "imageUrls":[
         "https://cdnimg.webstaurantstore.com/uploads/blog/2019/3/blog-types-pizza_in-blog-7.jpg"
      ],
      "description":"Reflecting the city’s deep ties to the auto industry, Detroit-style pizza was originally baked in a square automotive parts pan in the 1940’s. Detroit pizza is first topped with pepperoni, followed by brick cheese which is spread to the very edges of the pan, yielding a caramelized cheese perimeter. Sauce is then spooned over the pizza, an order similar to Chicago-style pizza. This pizza features a thick, extra crispy crust that is tender and airy on the inside."
   }
]
```
**Try**: https://springboot-kotlin-demo.herokuapp.com/pizza
**Exceptions**: 
`NOT_FOUND - 404` If the method doesn't match the required one `(GET)`

### Get a Pizza by id
**Path**: `/pizza/{id}` - {id} should be replaced by the id of the queried pizza
**Method**: `GET`
**Request Body**: N/A
**Parameters**: N/A
**Response**: `OK - 200`
```
{
   "id":2,
   "name":"Detroit",
   "price":999,
   "imageUrls":[
      "https://cdnimg.webstaurantstore.com/uploads/blog/2019/3/blog-types-pizza_in-blog-7.jpg"
   ],
   "description":"Reflecting the city’s deep ties to the auto industry, Detroit-style pizza was originally baked in a square automotive parts pan in the 1940’s. Detroit pizza is first topped with pepperoni, followed by brick cheese which is spread to the very edges of the pan, yielding a caramelized cheese perimeter. Sauce is then spooned over the pizza, an order similar to Chicago-style pizza. This pizza features a thick, extra crispy crust that is tender and airy on the inside."
}
```
**Try**: https://springboot-kotlin-demo.herokuapp.com/pizza/2
**Exceptions**: 
`NOT_FOUND - 404` If the id doesn't exist or the method doesn't match the required one `(GET)`

### Place Order
**Path**: `/pizza/order`
**Method**: `POST`
**Request Body**:  List of orders
```
[
   {
      "pizzaId":1,
      "quantity":2
   },
   {
      "pizzaId":2,
      "quantity":5
   },
   {
      "pizzaId":1,
      "quantity":3
   }
]
```
**Parameters**: N/A
**Response**: `OK - 200` (Empty body)
**Exceptions**: 
`BAD_REQUEST - 400` If the list is empty or the body doesn't match the required format
`NOT_FOUND - 404` If the method doesn't match the required one `(POST)`
