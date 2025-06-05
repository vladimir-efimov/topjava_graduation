app="http://localhost:8081"

user_operations() {
    echo -e "\nOperations with users"

    echo -e "\n"
    echo "Get all users"
    curl -s ${app}/rest/admin/users --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Get first user"
    curl -s ${app}/rest/admin/users/1 --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Find by e-mail"
    curl -s ${app}/rest/admin/users?email=admin@restaurants.ru --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Find by name"
    curl -s ${app}/rest/admin/users?name=Admin --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Add user"
    curl -s -i -X POST \
      -d '{"name":"New User","email":"new_user@mail.ru","roles":["USER"],"password":"test-password"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/users --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Get added user"
    curl -s ${app}/rest/admin/users/3 --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Update user"
    curl -s -i -X PUT \
      -d '{"id":3, "name":"New User","email":"New_User@mail.ru","password":"test-passwd", "enabled":"false"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
       ${app}/rest/admin/users/3 --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Get updated user"
    curl -s ${app}/rest/admin/users/3 --user admin@restaurants.ru:admin
}


user_illegal_operations() {
    echo -e "\n\n"
    echo -e "Illegal operations with users"

    echo -e "\n"
    echo "Try get user with illegal id"
    curl -s ${app}/rest/admin/users/0 --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Try get user with anauthorized request"
    curl -s ${app}/rest/admin/users/1

    echo -e "\n"
    echo "Try find by not existed e-mail"
    curl -s ${app}/rest/admin/users?email=does_not_exists --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Try find by not-existed name"
    curl -s ${app}/rest/admin/users?name=does_not_exists --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Try find by not-existed property"
    curl -s ${app}/rest/admin/users?some_property=does_not_exists --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Typo in URL"
    curl -s ${app}/rest/admin/user/1 --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Add user - send corrupted JSON"
    curl -s -i -X POST \
      -d '{"name":"New User","email" = "new_user@mail.ru","password":"test-password"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/users --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Try add user with missed name"
    curl -s -i -X POST \
      -d '{"email" : "new_user2@mail.ru","password":"test-password"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/users --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Try add user with incorrect email"
    curl -s -i -X POST \
      -d '{"name" : "New User" ,"email" : "new_usermail.ru","password":"test-password"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/users --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Try add user with incorrect password"
    curl -s -i -X POST \
      -d '{"name" : "New User" ,"email" : "new_user@mail.ru","password":"test"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/users --user admin@restaurants.ru:admin
}


profile_operations() {
    echo -e "\n\n"
    echo -e "Operations with profile"

    echo -e "\n"
    echo "Register user"
    curl -s -i -X POST \
      -d '{"name":"Registered _user","email":"reg@restaurants.ru","password":"test-password"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/profile

    echo -e "\n"
    echo "Update profile"
    curl -s -i -X PUT \
      -d '{"name":"Registered User","email":"reg@restaurants.ru","password":"test-password"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/profile --user "reg@restaurants.ru:test-password"

    echo -e "\n"
    echo "Get profile"
    curl -s ${app}/rest/profile --user "reg@restaurants.ru:test-password"

    echo -e "\n"
    echo "Delete profile"
    curl -s -X DELETE ${app}/rest/profile --user "reg@restaurants.ru:test-password"

    echo -e "\n"
    echo "Get all users"
    curl -s ${app}/rest/admin/users --user admin@restaurants.ru:admin
}


profile_illegal_operations() {
    echo -e "\n\n"
    echo -e "Illegal operations with profile"

    echo -e "\n"
    echo "Try get profile for disabled user"
    curl -s ${app}/rest/profile --user "New_User@mail.r:test-passwd"
}

restaurant_operations() {
    echo -e "\n\n"
    echo -e "Operations with restaurants"

    echo -e "\n"
    echo "Add restaurant"
    curl -s -i -X POST \
      -d '{"name":"cafe1","address":"street1"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/restaurants --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Get added restaurant"
    curl -s ${app}/rest/restaurants/1 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Add restaurant"
    curl -s -i -X POST \
      -d '{"name":"cafe2","address":"street2"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/restaurants --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Update restaurant"
    curl -s -i -X PUT \
      -d '{"id":1, "name":"cafe1","address":"street 1, 1"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
       ${app}/rest/admin/restaurants/1 --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Get updated restaurant"
    curl -s ${app}/rest/restaurants/1 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get all restaurants"
    curl -s ${app}/rest/restaurants --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get enabled restaurants"
    curl -s ${app}/rest/restaurants/enabled --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Find by name"
    curl -s ${app}/rest/restaurants?name=cafe2 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Find by address"
    curl -s ${app}/rest/restaurants?address=street2 --user user@restaurants.ru:user1
}

restaurant_illegal_operations() {
    echo -e "\n\n"
    echo -e "Illegal operations with restaurants"

    echo -e "\n"
    echo "Add restaurant with duplicate name and address"
    curl -s -i -X POST \
      -d '{"name":"cafe2","address":"street2"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/restaurants --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Add restaurant without name"
    curl -s -i -X POST \
      -d '{"address":"street1"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/restaurants --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Add restaurant without address"
    curl -s -i -X POST \
      -d '{"name":"cafe1"}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/restaurants --user admin@restaurants.ru:admin
}


dish_operations() {
    echo -e "\n\n"
    echo -e "Operations with dishs"

    echo -e "\n"
    echo "Add dish"
    for i in 1 2; do
        curl -s -i -X POST \
          -d "{\"name\":\"dish${i}\", \"price\":100.0, \"enabled\": \"true\", \"restaurantId\":1}" \
          -H 'Content-Type:application/json;charset=UTF-8' \
          ${app}/rest/admin/dishes --user admin@restaurants.ru:admin
        echo ""
    done

    echo -e "\n"
    echo "Update dish"
    curl -s -i -X PUT \
      -d '{"id":1, "name":"dish1", "price":111.5, "enabled": "true", "restaurantId":1}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
       ${app}/rest/admin/dishes/1 --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Get first dish"
    curl -s ${app}/rest/admin/dishes/1 --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Get enabled dishes"
    curl -s ${app}/rest/admin/dishes/enabled --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Get dishes for restaurant with id=1"
    curl -s ${app}/rest/admin/dishes?restaurantId=1 --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Get all dishes"
    curl -s ${app}/rest/admin/dishes --user admin@restaurants.ru:admin
}


dish_illegal_operations() {
    echo -e "\n\n"
    echo -e "Illegal operations with dishes"

    echo -e "\n"
    echo "Add dish without name"
    curl -s -i -X POST \
     -d '{"price":100.0, "enabled": "true", "restaurantId":1}' \
     -H 'Content-Type:application/json;charset=UTF-8' \
     ${app}/rest/admin/dishes --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Add dish without price"
    curl -s -i -X POST \
     -d '{"name": "new_dish", "enabled": "true", "restaurantId":1}' \
     -H 'Content-Type:application/json;charset=UTF-8' \
     ${app}/rest/admin/dishes --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Add dish without restaurant Id and price"
    curl -s -i -X POST \
     -d '{"name": "new_dish", "enabled": "true"}' \
     -H 'Content-Type:application/json;charset=UTF-8' \
     ${app}/rest/admin/dishes --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Add dish with bad price"
    curl -s -i -X POST \
     -d '{"name": "new_dish", "enabled": "true", "restaurantId":1, "price":"100.0 - 5% discount"}' \
     -H 'Content-Type:application/json;charset=UTF-8' \
     ${app}/rest/admin/dishes --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Update dish but loose id"
    curl -s -i -X PUT \
      -d '{"name":"dish1", "price":666.0, "enabled": "true", "restaurantId":1}' \
      -H 'Content-Type:application/json;charset=UTF-8' \
       ${app}/rest/admin/dishes/1 --user admin@restaurants.ru:admin
}


menu_operations() {
    echo -e "\n\n"
    echo -e "Operations with menus"

    echo -e "\n"
    today=$(date '+%Y-%m-%d')
    echo "Add menu for ${today}"
    curl -s -i -X POST \
      -d "{\"restaurantId\":1, \"date\":\"${today}\", \"dishes\": [1, 2]}" \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/menus --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Get single menu"
    curl -s ${app}/rest/menus/1 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get menus for restaurant"
    curl -s ${app}/rest/menus?restaurantId=1 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get menus for date"
    curl -s ${app}/rest/menus?date="${today}" --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get menus for restaurant and date"
    curl -s ${app}/rest/menus?"restaurantId=1&date=${today}" --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get all menus"
    curl -s ${app}/rest/menus --user user@restaurants.ru:user1
}


menu_illegal_operations() {
    echo -e "\n\n"
    echo -e "Illegal operations with menus"

    echo -e "\n"
    echo "Request not existed menu"
    curl -s ${app}/rest/menus/11 --user admin@restaurants.ru:admin

    echo -e "\n"
    today=$(date '+%Y-%m-%d')
    echo "Try adding duplicated menu"
    curl -s -i -X POST \
      -d "{\"restaurantId\":1, \"date\":\"${today}\", \"dishes\": [1, 2]}" \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/menus --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Try adding menu for old date"
    olddate="1900-01-01"
    curl -s -i -X POST \
      -d "{\"restaurantId\":1, \"date\":\"${olddate}\", \"dishes\": [1, 2]}" \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/menus --user admin@restaurants.ru:admin

    echo -e "\n"
    today=$(date '+%Y-%m-%d')
    echo "Try adding menu without restaurant"
    curl -s -i -X POST \
      -d "{\"date\":\"${today}\", \"dishes\": [1, 2]}" \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/menus --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Try adding menu without dishes"
    curl -s -i -X POST \
      -d "{\"restaurantId\":1, \"date\":\"${today}\"}" \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/menus --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Try adding menu with date in incorrect format"
    bad_formatted_date="1900/01/01"
    curl -s -i -X POST \
      -d "{\"restaurantId\":1, \"date\":\"${bad_formatted_date}\", \"dishes\": [1, 2]}" \
      -H 'Content-Type:application/json;charset=UTF-8' \
      ${app}/rest/admin/menus --user admin@restaurants.ru:admin

    echo -e "\n"
    echo "Try find menu using incorrect parameter value"
    curl -s ${app}/rest/menus?restaurantId=x --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Try find menu using incorrect parameter name"
    curl -s ${app}/rest/menus?restaurantName=cafe1 --user user@restaurants.ru:user1
}


vote_operations() {
    echo -e "\n\n"
    echo -e "Operations with votes"

    echo -e "\n"
    echo "Get end voting time"
    curl -s ${app}/rest/votes/end-voting-time --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Add vote"
    curl -s -X POST \
      ${app}/rest/votes?restaurantId=1 --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get users votes"
    curl -s ${app}/rest/votes --user user@restaurants.ru:user1

    echo -e "\n"
    echo "Get votes for date"
    today=$(date '+%Y-%m-%d')
    curl -s "${app}/rest/votes?date=${today}" --user user@restaurants.ru:user1
}


vote_illegal_operations() {
    echo -e "\n\n"
    echo -e "Illegal operations with votes"

    echo -e "\n"
    echo "Try vote without providing restaurant"
    curl -s -X POST \
      ${app}/rest/votes --user user@restaurants.ru:user1
}



user_operations
user_illegal_operations
profile_operations
profile_illegal_operations
restaurant_operations
restaurant_illegal_operations
dish_operations
dish_illegal_operations
menu_operations
menu_illegal_operations
vote_operations
vote_illegal_operations
echo ""
