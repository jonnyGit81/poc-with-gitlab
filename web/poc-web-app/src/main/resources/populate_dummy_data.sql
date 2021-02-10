-- USER THIS SCRIPT TO GENERATE MILLION OF RECORD ADMIN USER FOR PAGING TESTING

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `LoadAminUserData`(
    how_many INT
)
BEGIN

    DECLARE counter INT DEFAULT 1;
    DECLARE start_index INT DEFAULT 1;
    SET start_index = (select max(id) from admin_user);
    WHILE counter <= how_many DO
        START TRANSACTION;
		SET start_index = start_index + 1;
		insert into admin_user(created_date,version,access_level,deleted,deleted_time,email,last_login_time,login_time,login_fail_count,name,password,phone,status)
		select created_date,version,access_level,deleted,deleted_time, concat('adm@adm',start_index,'.com'),last_login_time,login_time,login_fail_count,concat(name,start_index),password,phone,status from admin_user where email = 'admin@admin.com';
        SET counter = counter + 1;
        COMMIT;
    END WHILE;
    SELECT 'TOTAL CREATED ROW ' + counter - 1;
END$$
DELIMITER ;


call LoadAminUserData(500000);
call LoadAminUserData(500000);
call LoadAminUserData(500000);
call LoadAminUserData(500000);