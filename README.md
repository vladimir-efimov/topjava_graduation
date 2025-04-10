# Graduation project for <a href="https://github.com/JavaOPs/topjava" target=_blank>TopJava course</a>

REST API (without frontend) for voting system for deciding where to have lunch.

## Technologies:
- Hibernate
- Spring/SpringMVC

## Functionality

Users can vote for a restaurant they want to have a lunch today. Only one vote is counted per user.

If user votes again the same day:
- If it is before 11:00 we assume that he changed his mind.
- If it is after 11:00 then it is too late, vote can't be changed

Restaurants may provide a new menu each day updated by administrators.

## REST API

Unfortunately Swagger doesn't work with Jakarta. So currently REST API can be picked up from curl_test.sh.

## Notes on business logic

Application preserves history. The only object which can be deleted is vote during voting time.
User can delete himself but his data actually are erased, anonimized history of voting is available for analysis.
Meals and restaurants can be disabled by administrator.
