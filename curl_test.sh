user_operations() {
    echo -e "\nOperations with users\n"

    echo "Get all users"
    curl -s http://localhost:8080/topjava-graduation/rest/users

    echo -e "\n"
    echo "Get first user"
    curl -s http://localhost:8080/topjava-graduation/rest/users/1

    echo -e "\n"
    echo "Find by e-mail"
    curl -s http://localhost:8080/topjava-graduation/rest/users/find?email=admin@restaurants.ru

    echo -e "\n"
    echo "Find by name"
    curl -s http://localhost:8080/topjava-graduation/rest/users/filter?name=Admin

    echo -e "\n"
    echo "Add user"
    curl -s -i -X POST \
      -d '{"name":"New User","email":"new_user@mail.ru","password":"test-password"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/users

    echo -e "\n"
    echo "Get added user"
    curl -s http://localhost:8080/topjava-graduation/rest/users/3

    echo -e "\n"
    echo "Update user"
    curl -s -i -X PUT \
      -d '{"id":3, "name":"New User","email":"New_User@mail.ru","password":"test-passwd"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
       http://localhost:8080/topjava-graduation/rest/users/3

    echo -e "\n"
    echo "Get updated user"
    curl -s http://localhost:8080/topjava-graduation/rest/users/3
}


user_illegal_operations() {
    echo -e "\nIllegal operations with users\n"

    echo "Try get user with illegal id"
    curl -s http://localhost:8080/topjava-graduation/rest/users/0

    echo -e "\n"
    echo "Try find by not existed e-mail"
    curl -s http://localhost:8080/topjava-graduation/rest/users/find?email=does_not_exists

    echo -e "\n"
    echo "Try find by not-existed name"
    curl -s http://localhost:8080/topjava-graduation/rest/users/filter?name=does_not_exists
}


restaurant_operations() {
    echo -e "\nOperations with restaurants\n"

    echo -e "\n"
    echo "Add restaurant"
    curl -s -i -X POST \
      -d '{"name":"cafe1","address":"street1"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/restaurants

    echo -e "\n"
    echo "Get added restaurant"
    curl -s http://localhost:8080/topjava-graduation/rest/restaurants/1

    echo -e "\n"
    echo "Add restaurant"
    curl -s -i -X POST \
      -d '{"name":"cafe2","address":"street2"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/restaurants

    echo -e "\n"
    echo "Update restaurant"
    curl -s -i -X PUT \
      -d '{"id":1, "name":"cafe1","address":"street 1, 1"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
       http://localhost:8080/topjava-graduation/rest/restaurants/1

    echo -e "\n"
    echo "Get updated restaurant"
    curl -s http://localhost:8080/topjava-graduation/rest/restaurants/1

    echo -e "\n"
    echo "Get all restaurants"
    curl -s http://localhost:8080/topjava-graduation/rest/restaurants

    echo -e "\n"
    echo "Find by name"
    curl -s http://localhost:8080/topjava-graduation/rest/restaurants/filter?name=cafe2
}


meal_operations() {
    echo -e "\nOperations with meals\n"

    echo -e "\n"
    echo "Add meal"
    for i in 1 2; do
        curl -s -i -X POST \
         -d "{\"name\":\"meal${i}\", \"price\":100.0, \"enabled\": \"true\", \"restaurantId\":1}" \
         -H 'Content-Type:application/json;charset=UTF-8' \
         http://localhost:8080/topjava-graduation/rest/meals
    done

    echo -e "\n"
    echo "Get all meals"
    curl -s http://localhost:8080/topjava-graduation/rest/meals
}


menu_operations() {
    echo -e "\nOperations with menus\n"

    echo -e "\n"
    today=$(date '+%Y-%m-%d')
    echo "Add menu for ${today}"
    curl -s -i -X POST \
      -d "{\"restaurantId\":1, \"date\":\"${today}\", \"meals\": [1, 2]}" \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/menus

    echo -e "\n"
    echo "Get single menu"
    curl -s http://localhost:8080/topjava-graduation/rest/menus/1

    echo -e "\n"
    echo "Get menus for restaurant"
    curl -s "http://localhost:8080/topjava-graduation/rest/menus/find?restaurantId=1"

    echo -e "\n"
    echo "Get menus for date"
    curl -s "http://localhost:8080/topjava-graduation/rest/menus/find?date=${today}"

    echo -e "\n"
    echo "Get menus for restaurant and date"
    curl -s "http://localhost:8080/topjava-graduation/rest/menus/find?restaurantId=1&date=${today}"

    echo -e "\n"
    echo "Get all menus"
    curl -s http://localhost:8080/topjava-graduation/rest/menus
}


user_operations
restaurant_operations
meal_operations
menu_operations
