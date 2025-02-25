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

    # detached entity passed to persist - !!!
    echo -e "\n"
    echo "Add meal"
    curl -s -i -X POST \
      -d '{"name":"meal1", "price":100.0, "restaurant_id":1}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/meals

    echo -e "\n"
    echo "Get all meals"
    curl -s http://localhost:8080/topjava-graduation/rest/meals

}

user_operations
restaurant_operations
meal_operations
