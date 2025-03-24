user_operations() {
    echo -e "\nOperations with users"

    echo -e "\n"
    echo "Get all users"
    curl -s http://localhost:8080/topjava-graduation/rest/users --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get first user"
    curl -s http://localhost:8080/topjava-graduation/rest/users/1 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Find by e-mail"
    curl -s http://localhost:8080/topjava-graduation/rest/users/find?email=admin@restaurants.ru --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Find by name"
    curl -s http://localhost:8080/topjava-graduation/rest/users/filter?name=Admin --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Add user"
    curl -s -i -X POST \
      -d '{"name":"New User","email":"new_user@mail.ru","password":"test-password"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/users --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get added user"
    curl -s http://localhost:8080/topjava-graduation/rest/users/3 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Update user"
    curl -s -i -X PUT \
      -d '{"id":3, "name":"New User","email":"New_User@mail.ru","password":"test-passwd"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
       http://localhost:8080/topjava-graduation/rest/users/3 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get updated user"
    curl -s http://localhost:8080/topjava-graduation/rest/users/3 --user user@restaurants.ru:user1
}


user_illegal_operations() {
    echo -e "\n\n"
    echo -e "Illegal operations with users"

    echo -e "\n"
    echo "Try get user with illegal id"
    curl -s http://localhost:8080/topjava-graduation/rest/users/0 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Try get user with anauthorized request"
    curl -s http://localhost:8080/topjava-graduation/rest/users/1

    echo -e "\n"
    echo "Try find by not existed e-mail"
    curl -s http://localhost:8080/topjava-graduation/rest/users/find?email=does_not_exists --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Try find by not-existed name"
    curl -s http://localhost:8080/topjava-graduation/rest/users/filter?name=does_not_exists --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Try find by not-existed property"
    curl -s http://localhost:8080/topjava-graduation/rest/users/filter?some_property=does_not_exists --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Typo in URL"
    curl -s http://localhost:8080/topjava-graduation/rest/user/1 --user user@restaurants.ru:user1 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Add user - send corrupted JSON"
    curl -s -i -X POST \
      -d '{"name":"New User","email" = "new_user@mail.ru","password":"test-password"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/users --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Try add user with missed name"
    curl -s -i -X POST \
      -d '{"email" : "new_user2@mail.ru","password":"test-password"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/users --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Try add user with incorrect email"
    curl -s -i -X POST \
      -d '{"name" : "New User" ,"email" : "new_usermail.ru","password":"test-password"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/users --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Try add user with incorrect password"
    curl -s -i -X POST \
      -d '{"name" : "New User" ,"email" : "new_user@mail.ru","password":"test"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/users --user user@restaurants.ru:user1
}


restaurant_operations() {
    echo -e "\n\n"
    echo -e "Operations with restaurants"

    echo -e "\n"
    echo "Add restaurant"
    curl -s -i -X POST \
      -d '{"name":"cafe1","address":"street1"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/restaurants --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get added restaurant"
    curl -s http://localhost:8080/topjava-graduation/rest/restaurants/1 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Add restaurant"
    curl -s -i -X POST \
      -d '{"name":"cafe2","address":"street2"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/restaurants --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Update restaurant"
    curl -s -i -X PUT \
      -d '{"id":1, "name":"cafe1","address":"street 1, 1"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
       http://localhost:8080/topjava-graduation/rest/restaurants/1 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get updated restaurant"
    curl -s http://localhost:8080/topjava-graduation/rest/restaurants/1 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get all restaurants"
    curl -s http://localhost:8080/topjava-graduation/rest/restaurants --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Find by name"
    curl -s http://localhost:8080/topjava-graduation/rest/restaurants/filter?name=cafe2 --user user@restaurants.ru:user1
}

restaurant_illegal_operations() {
    echo -e "\n\n"
    echo -e "Illegal operations with restaurants"

    echo -e "\n"
    echo "Add restaurant with duplicate name and address"
    curl -s -i -X POST \
      -d '{"name":"cafe2","address":"street2"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/restaurants --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Add restaurant without name"
    curl -s -i -X POST \
      -d '{"address":"street1"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/restaurants --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Add restaurant without address"
    curl -s -i -X POST \
      -d '{"name":"cafe1"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/restaurants --user user@restaurants.ru:user1
}


meal_operations() {
    echo -e "\n\n"
    echo -e "Operations with meals"

    echo -e "\n"
    echo "Add meal"
    for i in 1 2; do
        curl -s -i -X POST \
         -d "{\"name\":\"meal${i}\", \"price\":100.0, \"enabled\": \"true\", \"restaurantId\":1}" \
         -H 'Content-Type:application/json;charset=UTF-8' \
         http://localhost:8080/topjava-graduation/rest/meals --user user@restaurants.ru:user1
    done

    echo -e "\n"
    echo "Get all meals"
    curl -s http://localhost:8080/topjava-graduation/rest/meals --user user@restaurants.ru:user1
}


meal_illegal_operations() {
    echo -e "\n\n"
    echo -e "Illegal operations with meals"

    echo -e "\n"
    echo "Add meal without name"
    curl -s -i -X POST \
     -d '{"price":100.0, "enabled": "true", "restaurantId":1}' \
     -H 'Content-Type:application/json;charset=UTF-8' \
     http://localhost:8080/topjava-graduation/rest/meals --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Add meal without price"
    curl -s -i -X POST \
     -d '{"name": "new_meal", "enabled": "true", "restaurantId":1}' \
     -H 'Content-Type:application/json;charset=UTF-8' \
     http://localhost:8080/topjava-graduation/rest/meals --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Add meal without restaurant Id and price"
    curl -s -i -X POST \
     -d '{"name": "new_meal", "enabled": "true"}' \
     -H 'Content-Type:application/json;charset=UTF-8' \
     http://localhost:8080/topjava-graduation/rest/meals --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Add meal with bad price"
    curl -s -i -X POST \
     -d '{"name": "new_meal", "enabled": "true", "restaurantId":1, "price":"100.0 - 5% discount"}' \
     -H 'Content-Type:application/json;charset=UTF-8' \
     http://localhost:8080/topjava-graduation/rest/meals --user user@restaurants.ru:user1
}


menu_operations() {
    echo -e "\n\n"
    echo -e "Operations with menus"

    echo -e "\n"
    today=$(date '+%Y-%m-%d')
    echo "Add menu for ${today}"
    curl -s -i -X POST \
      -d "{\"restaurantId\":1, \"date\":\"${today}\", \"meals\": [1, 2]}" \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/menus --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get single menu"
    curl -s http://localhost:8080/topjava-graduation/rest/menus/1 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get menus for restaurant"
    curl -s "http://localhost:8080/topjava-graduation/rest/menus/find?restaurantId=1"

    echo -e "\n"
    echo "Get menus for date"
    curl -s http://localhost:8080/topjava-graduation/rest/menus/find?date="${today}" --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get menus for restaurant and date"
    curl -s http://localhost:8080/topjava-graduation/rest/menus/find?restaurantId=1&date="${today}" --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get all menus"
    curl -s http://localhost:8080/topjava-graduation/rest/menus --user user@restaurants.ru:user1
}


menu_illegal_operations() {
    echo -e "\n\n"
    echo -e "Illegal operations with menus"

    echo -e "\n"
    echo "Request not existed menu"
    curl -s http://localhost:8080/topjava-graduation/rest/menus/11 --user user@restaurants.ru:user1

    echo -e "\n"
    today=$(date '+%Y-%m-%d')
    echo "Try adding duplicated menu"
    curl -s -i -X POST \
      -d "{\"restaurantId\":1, \"date\":\"${today}\", \"meals\": [1, 2]}" \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/menus --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Try adding menu for old date"
    olddate="1900-01-01"
    curl -s -i -X POST \
      -d "{\"restaurantId\":1, \"date\":\"${olddate}\", \"meals\": [1, 2]}" \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/menus --user user@restaurants.ru:user1

    echo -e "\n"
    today=$(date '+%Y-%m-%d')
    echo "Try adding menu without restaurant"
    curl -s -i -X POST \
      -d "{\"date\":\"${today}\", \"meals\": [1, 2]}" \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/menus --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Try adding menu without meals"
    curl -s -i -X POST \
      -d "{\"restaurantId\":1, \"date\":\"${today}\"}" \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/menus --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Try adding menu with date in incorrect format"
    bad_formatted_date="1900/01/01"
    curl -s -i -X POST \
      -d "{\"restaurantId\":1, \"date\":\"${bad_formatted_date}\", \"meals\": [1, 2]}" \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/menus --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Try find menu using incorrect parameters"
    curl -s http://localhost:8080/topjava-graduation/rest/menus/find?restaurant_id=x --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Try find menu using incorrect parameters"
    curl -s http://localhost:8080/topjava-graduation/rest/menus/find?restaurant_name=cafe1 --user user@restaurants.ru:user1
}


vote_operations() {
    echo -e "\n\n"
    echo -e "Operations with votes"

    echo -e "\n"
    echo "Get end voting time"
    curl -s http://localhost:8080/topjava-graduation/rest/admin/votes/end_voting_time --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Set end voting time"
    curl -s -i -X PUT \
      http://localhost:8080/topjava-graduation/rest/admin/votes/end_voting_time?time="23:59:59" --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get end voting time"
    curl -s http://localhost:8080/topjava-graduation/rest/votes/end_voting_time --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Add vote"
    curl -s -i -X POST \
      -d '{"restaurantId":1, "userId": 1}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/votes --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get single vote"
    curl -s http://localhost:8080/topjava-graduation/rest/votes/1 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get votes for date"
    today=$(date '+%Y-%m-%d')
    curl -s "http://localhost:8080/topjava-graduation/rest/votes/find?date=${today}" --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get all votes"
    curl -s http://localhost:8080/topjava-graduation/rest/votes --user user@restaurants.ru:user1

}


vote_illegal_operations() {
    echo -e "\n\n"
    echo -e "Illegal operations with votes"

    echo -e "\n"
    echo "Try set incorrect end voting time"
    curl -s -i -X PUT \
      http://localhost:8080/topjava-graduation/rest/admin/votes/end_voting_time?time="23-59" --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Try adding vote without restaurant"
    curl -s -i -X POST \
      -d '{"userId": 1}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/votes --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Try adding vote without restaurant"
    curl -s -i -X POST \
      -d '{"restaurantId": 1}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      http://localhost:8080/topjava-graduation/rest/votes --user user@restaurants.ru:user1
}


get_elected() {
    echo -e "\n"
    echo "Set end voting time to past"
    curl -s -i -X PUT \
      http://localhost:8080/topjava-graduation/rest/admin/votes/end_voting_time?time="$(date '+%H:%m')" --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get elected restaurant"
    curl -s http://localhost:8080/topjava-graduation/rest/votes/elected --user user@restaurants.ru:user1
}

user_operations
user_illegal_operations
restaurant_operations
restaurant_illegal_operations
meal_operations
meal_illegal_operations
menu_operations
menu_illegal_operations
vote_operations
vote_illegal_operations
get_elected
