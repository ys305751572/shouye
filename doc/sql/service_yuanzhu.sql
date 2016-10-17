
/** ALTER TABLE `table_name` ADD INDEX index_name ( `column1`, `column2`, `column3` ) **/

ALTER  TABLE `coinlog` ADD INDEX index_name (`stuUserID`,`status1`,`type`);

ALTER  TABLE `feedback` ADD INDEX index_name (`stuUserID`,`status`);

ALTER  TABLE `gift` ADD INDEX index_name (`g_status`,`status`);

ALTER  TABLE `groupinfo` ADD INDEX index_name (`provinceId`,`cityId`,`schoolId`,`status`);

ALTER TABLE `groupmember` ADD PRIMARY KEY ( `groupId`,`stuUserId`);

ALTER TABLE `placard` ADD PRIMARY KEY ( `provinceId`,`cityId`,`schoolId`,`collegeId`);

ALTER TABLE `post` ADD PRIMARY KEY ( `user_id`,`post_bar_id`,`is_list`,`is_top`);

ALTER TABLE `post_comment` ADD PRIMARY KEY ( `from_user_id`,`to_user_id`,`comment_id`,`post_id`);

ALTER TABLE `post_image` ADD PRIMARY KEY ( `post_id`);

ALTER TABLE `post_praise` ADD PRIMARY KEY ( `post_id`,`user_id`);

ALTER TABLE `scs` ADD PRIMARY KEY ( `id`,`pid`,`provinceId`,`level`,`cityId`);

