-- MODULES --
INSERT INTO MODULE(created_date, version, display_order, icon_name, label, name, url, parent_id)
SELECT current_timestamp(), 0, 1, 'cog', 'Settings', 'settings', '/setting', null FROM DUAL WHERE NOT EXISTS (Select id From module where name ='settings');

INSERT INTO MODULE(created_date, version, display_order, icon_name, label, name, url, parent_id)
SELECT current_timestamp(), 0, 1, 'user', 'Users', 'user', '/users', (select id from module where name ='settings') FROM DUAL WHERE NOT EXISTS (Select id From module where name ='user');

INSERT INTO MODULE(created_date, version, display_order, icon_name, label, name, url, parent_id)
SELECT current_timestamp(), 0, 2, 'view-cards', 'Access Profiles', 'acessProfiles', '/access-profiles', (select id from module where name ='settings') FROM DUAL WHERE NOT EXISTS (Select id From module where name ='acessProfiles');

INSERT INTO MODULE(created_date, version, display_order, icon_name, label, name, url, parent_id)
SELECT current_timestamp(), 0, 3, 'file-group', 'Modules', 'modules', '/modules', (select id from module where name ='settings') FROM DUAL WHERE NOT EXISTS (Select id From module where name ='modules');

INSERT INTO MODULE(created_date, version, display_order, icon_name, label, name, url, parent_id)
SELECT current_timestamp(), 0, 4, 'form', 'Pages', 'pages', '/pages', (select id from module where name ='settings') FROM DUAL WHERE NOT EXISTS (Select id From module where name ='pages');


INSERT INTO MODULE(created_date, version, display_order, icon_name, label, name, url, parent_id)
SELECT current_timestamp(), 0, 2, 'view-cards', 'Crud', 'crud', '/crud', null FROM DUAL WHERE NOT EXISTS (Select id From module where name ='crud');

INSERT INTO MODULE(created_date, version, display_order, icon_name, label, name, url, parent_id)
SELECT current_timestamp(), 0, 1, 'form', 'Template', 'templateCrud', '/template-crud', (select id from module where name ='crud') FROM DUAL WHERE NOT EXISTS (Select id From module where name ='templateCrud');

INSERT INTO MODULE(created_date, version, display_order, icon_name, label, name, url, parent_id)
SELECT current_timestamp(), 0, 2, 'table', 'Reactive', 'reactiveCrud', '/reactive-crud', (select id from module where name ='crud') FROM DUAL WHERE NOT EXISTS (Select id From module where name ='reactiveCrud');

-- END MODULES --

-- PAGES -- [ *IMPORTANT NOTE Pattern for page url => /module.url/<any_page_url> ]
    -- USER
    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 0, 'List User', 'listUser', '/users/list', 1,
    (Select id from module where name = 'user'), (Select parent_id from module where name = 'user')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='listUser');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 1, 'Add User', 'addUser', '/users/add', 1,
    (Select id from module where name = 'user'), (Select parent_id from module where name = 'user')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='addUser');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 2, 'Edit User', 'editUser', 'users/edit', 0,
    (Select id from module where name = 'user'), (Select parent_id from module where name = 'user')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='editUser');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 3, 'Delete User', 'deleteUser', '/users/delete', 0,
    (Select id from module where name = 'user'), (Select parent_id from module where name = 'user')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='deleteUser');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 4, 'View User', 'viewUser', '/users/view', 0,
    (Select id from module where name = 'user'), (Select parent_id from module where name = 'user')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='viewUser');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 5, 'Assign Access Profiles', 'assignAccessProfiles', '/users/grant-access', 0,
    (Select id from module where name = 'user'), (Select parent_id from module where name = 'user')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='assignAccessProfiles');
    -- END USER

    -- ACCESS PROFILES
    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 0, 'List Access Profile', 'listAccessProfiles', '/access-profiles/list', 1,
    (Select id from module where name = 'acessProfiles'), (Select parent_id from module where name = 'acessProfiles')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='listAccessProfiles');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 1, 'Add User', 'addAccessProfiles', '/access-profiles/add', 1,
    (Select id from module where name = 'acessProfiles'), (Select parent_id from module where name = 'acessProfiles')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='addAccessProfiles');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 2, 'Edit User', 'editAccessProfiles', '/access-profiles/edit', 0,
    (Select id from module where name = 'acessProfiles'), (Select parent_id from module where name = 'acessProfiles')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='editAccessProfiles');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 3, 'Delete Access Profiles', 'deleteAccessProfiles', '/access-profiles/delete', 0,
    (Select id from module where name = 'acessProfiles'), (Select parent_id from module where name = 'acessProfiles')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='deleteAccessProfiles');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 4, 'View Access Profiles', 'viewAccessProfiles', '/view-access-profiles', 0,
    (Select id from module where name = 'acessProfiles'), (Select parent_id from module where name = 'acessProfiles')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='viewAccessProfiles');
    -- END ACCESS PROFILES

    -- MODULES
    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 0, 'List Modules', 'listModules', '/modules/add', 1,
    (Select id from module where name = 'modules'), (Select parent_id from module where name = 'modules')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='listModules');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 1, 'Add Modules', 'addModules', '/modules/add', 1,
    (Select id from module where name = 'modules'), (Select parent_id from module where name = 'modules')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='addModules');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 2, 'Edit Modules', 'editModules', '/modules/edit', 0,
    (Select id from module where name = 'modules'), (Select parent_id from module where name = 'modules')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='editModules');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 3, 'Delete Modules', 'deleteModules', '/modules/delete', 0,
    (Select id from module where name = 'modules'), (Select parent_id from module where name = 'modules')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='deleteModules');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 4, 'View Modules', 'viewModules', '/modules/view', 0,
    (Select id from module where name = 'modules'), (Select parent_id from module where name = 'modules')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='viewModules');
    -- END MODULES

    -- PAGE
    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 0, 'List Pages', 'listPages', '/pages/list', 1,
    (Select id from module where name = 'pages'), (Select parent_id from module where name = 'pages')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='listPages');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 1, 'Add Pages', 'addPages', '/pages/add', 1,
    (Select id from module where name = 'pages'), (Select parent_id from module where name = 'pages')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='addPages');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 2, 'Edit Pages', 'editPages', '/pages/edit', 0,
    (Select id from module where name = 'pages'), (Select parent_id from module where name = 'pages')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='editPages');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 3, 'Delete Pages', 'deletePages', '/pages/delete', 0,
    (Select id from module where name = 'pages'), (Select parent_id from module where name = 'pages')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='deletePages');

    INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
    SELECT current_timestamp(), 0, 0, 4, 'View Pages', 'viewPages', '/pages/view', 0,
    (Select id from module where name = 'pages'), (Select parent_id from module where name = 'pages')
    FROM DUAL WHERE NOT EXISTS (Select id From page where name ='viewPages');
    -- END PAGE

     -- DUMMY
        INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
        SELECT current_timestamp(), 0, 0, 1, 'List Template', 'listTemplate', '/template-crud/list', 1,
        (Select id from module where name = 'templateCrud'), (Select parent_id from module where name = 'templateCrud')
        FROM DUAL WHERE NOT EXISTS (Select id From page where name ='listTemplate');

        INSERT INTO page(created_date, version, admin_page, display_order, label, name, url, visible, module_id, root_module_id)
        SELECT current_timestamp(), 0, 0, 1, 'List Reactive', 'listReactive', '/reactive-crud/list', 1,
        (Select id from module where name = 'reactiveCrud'), (Select parent_id from module where name = 'reactiveCrud')
        FROM DUAL WHERE NOT EXISTS (Select id From page where name ='listReactive');

    -- END DUMMY
-- END PAGES --

-- CREATE ADMIN USER --
insert into admin_user(created_date, version, access_level, deleted, email, login_fail_count, name, password,status,maker_checker_role)
select current_timestamp(), 0, 'SYSTEM', 0, 'admin@admin.com', 0, 'Jonny',
'$2a$10$SsEYBuWDktd4yoq50.6b8.nHqRCUEZ9gsZVvoCWQ6Ug6AOwISgFUq', 'ACTIVE', 'APPROVER'
from dual where not exists (select id from admin_user where email = 'admin@admin.com');
