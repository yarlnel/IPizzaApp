package com.example.ipizzaapp.similar_db

import android.os.Parcelable
import com.example.ipizzaapp.pojo.Pizza
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

object PizzaDatabase {
    const val VERSION = 1
    const val NAME = "MY_PIZZA_DATABASE_NAME"

    private val pizzaList: List<PizzaEntity> = pizzaSet.mapIndexed { index, item ->
        PizzaEntity(
            index + 1,
            item.name,
            item.price,
            item.imageUrl,
            item.description
        )
    }

    val pizzaDao: PizzaDao = PizzaDao(pizzaList)
}

class PizzaDao (private val pizzaList: List<PizzaEntity>) {
    fun getAll(): List<PizzaEntity> = pizzaList

    fun getById(id: Int): PizzaEntity? = pizzaList.find { pizza -> pizza.id == id }

    fun <T> query(rawQuery: (List<PizzaEntity>) -> T): T = rawQuery(pizzaList)
}

fun oldPizzaToNew(oldPizza: OldPizza) : Pizza
    = Pizza (
        price = oldPizza.price.toInt(),
        imageUrls = listOf(oldPizza.imageUrl),
        name = oldPizza.name,
        description = oldPizza.description,
        id = (1..9).random()
    )

data class PizzaEntity(
    val id: Int,
    override val name: String,
    override val price: Double,
    override val imageUrl: String,
    override val description: String
) : OldPizza(
    name,
    price,
    imageUrl,
    description
)


@Parcelize
open class OldPizza(
    open val name: String,
    open val price: Double,
    open val imageUrl: String,
    open val description: String
) : Parcelable

val pizzaSet = listOf<OldPizza>(
    OldPizza(
        "Margarita",
        799.0,
        "https://static.1000.menu/img/content-v2/ef/27/10853/picca-margarita-v-domashnix-usloviyax_1608783820_4_max.jpg",
        "Pizza Margherita (more commonly known in English as Margherita pizza) is a " +
            "typical Neapolitan pizza, made with San Marzano tomatoes, mozzarella cheese, fresh " +
            "basil, salt, and extra-virgin olive oil."
    ),
    OldPizza(
        "Detroit",
        999.0,
        "https://cdnimg.webstaurantstore.com/uploads/blog/2019/3/blog-types-pizza_in-blog-7.jpg",
        "Reflecting the city’s deep ties to the auto industry, Detroit-style pizza was " +
            "originally baked in a square automotive parts pan in the 1940’s. Detroit pizza is " +
            "first topped with pepperoni, followed by brick cheese which is spread to the very " +
            "edges of the pan, yielding a caramelized cheese perimeter. Sauce is then spooned " +
            "over the pizza, an order similar to Chicago-style pizza. This pizza features a " +
            "thick, extra crispy crust that is tender and airy on the inside."
    ),
    OldPizza(
        "Greek Pizza",
        699.0,
        "https://cdnimg.webstaurantstore.com/uploads/blog/2016/8/onions.jpg",
        "Greek pizza was created by Greek immigrants who came to America and were " +
            "introduced to Italian pizza. Greek-style pizza, especially popular in the New " +
            "England states, features a thick and chewy crust cooked in shallow, oiled pans, " +
            "resulting in a nearly deep-fried bottom. While this style has a crust that is " +
            "puffier and chewier than thin crust pizzas, it’s not quite as thick as a deep-dish " +
            "or Sicilian crust."
    ),
    OldPizza(
        "New York-Style Pizza",
        560.0,
        "https://cdnimg.webstaurantstore.com/uploads/blog/2016/8/flat.jpg",
        "With its characteristic large, foldable slices and crispy outer crust, " +
            "New York-style pizza is one of America’s most famous regional pizza types. " +
            "Originally a variation of Neapolitan-style pizza, the New York slice has taken " +
            "on a fame all its own, with some saying its unique flavor has to do with the " +
            "minerals present in New York’s tap water supply."
    ),
    OldPizza(
        "California",
        910.0,
        "https://cdnimg.webstaurantstore.com/uploads/buying_guide/2014/11/pizzatypes-gourmet.jpg",
        "California pizza, or gourmet pizza, is known for its unusual ingredients. " +
            "This pizza got its start back in the late 1970’s when Chef Ed LaDou began " +
            "experimenting with pizza recipes in the classic Italian restaurant, Prego. He " +
            "created a pizza with mustard, ricotta, pate, and red pepper, and by chance, " +
            "served it to Wolfgang Puck. Impressed with LaDou’s innovative pie, Puck invited " +
            "him to be a head pizza chef at his restaurant. It was here that LaDou came up " +
            "with over 250 unique pizza recipes that eventually formed the menu of the chain " +
            "restaurant California Pizza Kitchen."
    ),
    OldPizza(
        "Chicago",
        460.0,
        "https://cdnimg.webstaurantstore.com/uploads/buying_guide/2014/11/pizzatypes-deepdish.jpg",
        "Chicago pizza, also commonly referred to as deep-dish pizza, gets its name " +
            "from the city it was invented in. During the early 1900’s, Italian immigrants in " +
            "the windy city were searching for something similar to the Neapolitan pizza that " +
            "they knew and loved. Instead of imitating the notoriously thin pie, Ike Sewell " +
            "had something else in mind. He created a pizza with a thick crust that had raised " +
            "edges, similar to a pie, and ingredients in reverse, with slices of mozzarella " +
            "lining the dough followed by meat, vegetables, and then topped with a can of " +
            "crushed tomatoes. This original creation led Sewell to create the now famous " +
            "chain restaurant, Pizzeria Uno."
    )
)

fun getTestPizzas () : List<Pizza> {
    val testPizzas = mutableListOf<Pizza>()
    val lorem = """
                Что такое Lorem Ipsum?
                Lorem Ipsum - это текст-"рыба",
                 часто используемый в печати и вэб-дизайне. 
                 Lorem Ipsum является стандартной "рыбой"
                  для текстов на латинице с начала 
                  XVI века. В то время некий безымянный
                   печатник создал большую коллекцию 
                   размеров и форм шрифтов, используя
                    Lorem Ipsum для распечатки образцов.
                     Lorem Ipsum не только успешно 
                     пережил без заметных изменений пять
                      веков, но и перешагнул в электронный
                       дизайн. Его популяризации в новое
                        время послужили публикация листов
                         Letraset с образцами Lorem Ipsum
                          в 60-х годах и, в более недавнее 
                          время, программы электронной вёрстки
                           типа Aldus PageMaker, в шаблонах 
                           которых используется Lorem Ipsum.

                Почему он используется?
                Давно выяснено, что при оценке дизайна и композиции читаемый 
                текст мешает сосредоточиться. Lorem Ipsum используют потому, 
                что тот обеспечивает более или менее стандартное заполнение шаблона,
                 а также реальное распределение букв и пробелов в абзацах,
                  которое не получается при простой дубликации
                   "Здесь ваш текст.. Здесь ваш текст.. Здесь ваш текст.."
                    Многие программы электронной вёрстки и редакторы HTML 
                    используют Lorem Ipsum в качестве текста по умолчанию, 
                    так что поиск по ключевым словам "lorem ipsum" 
                    сразу показывает, как много веб-страниц
                     всё ещё дожидаются своего настоящего рождения. 
                     За прошедшие годы текст Lorem Ipsum получил много версий.
                      Некоторые версии появились по ошибке, некоторые
                       - намеренно (например, юмористические варианты).

            """.trimIndent()
    val listOfCatPizzaImageUrls = listOf(
        "https://cdn.shopify.com/s/files/1/0344/6469/products/pizza_bed_1_1024x1024.jpg?v=1587691796",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQM-qbdmO-FVJlc6SAwPwsupcFvm6JUFOjcVw&usqp=CAU",
    )
    testPizzas.add(Pizza(
            price = 1234,
            imageUrls = listOfCatPizzaImageUrls,
            name = "KotoPizza",
            description = lorem,
        id = 123,
    ))
    val seaPizzasImageUrls = listOf(
        "https://www.crushpixel.com/big-static18/preview4/sea-pizza-with-shrimp-cheese-2849386.jpg",
        "https://previews.123rf.com/images/marcomayer/marcomayer1202/marcomayer120200194/12397156-pizza-with-sea-fruits.jpg",
        "https://d2gg9evh47fn9z.cloudfront.net/800px_COLOURBOX9937590.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQeosCjo1ll15w3o5o-6RlGkWAi5NCPuLTraA&usqp=CAU",
        )
    testPizzas.add(Pizza(
        price = 1234,
        imageUrls = seaPizzasImageUrls,
        name = "KotoPizza",
        description = lorem,
        id = 113,
    ))

    return testPizzas

}
