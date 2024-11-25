__config() -> {
    'stay_loaded' -> 'true',
    'scope' -> 'global'
};

__on_start() -> (
    // 创建 'tools_used_all' 计分板（挖掘榜）
    if (scoreboard('tools_used_all') == null,
        scoreboard_add('tools_used_all');
    );

    // 创建 'online_time' 计分板（在线时长）
    if (scoreboard('online_time') == null,
        scoreboard_add('online_time');
    );

    // 设置计分板显示名称
    scoreboard_property('tools_used_all', 'display_name', '§c§l挖 掘 榜');
    scoreboard_property('online_time', 'display_name', '§b§l在线时长（分钟）');

    // 初始化显示计分板
    scoreboard_display('sidebar', 'online_time');

    // 初始化全局变量，用于计分板切换
    global_switch = 'online_time';
    global_switch_time = 300;
    global_counter = 0;
);

__on_tick() -> (
    //轮播两个榜
    global_counter += 1;
    if (global_counter == global_switch_time,
        global_counter = 0;
        // 如果当前显示 'tools_used_all'，则切换到 'online_time'，否则切换回 'tools_used_all'
        if (global_switch == 'tools_used_all', global_switch = 'online_time',global_switch == 'online_time', global_switch = 'tools_used_all');
        // 更新计分板显示
        scoreboard_display('sidebar', global_switch);
    );

    for (player('all'),
        if (_~'player_type' != 'fake',
            // 统计工具使用次数
            tools_used_score = (
                statistic(_~'name', 'used', 'netherite_pickaxe') +
                statistic(_~'name', 'used', 'diamond_pickaxe') +
                statistic(_~'name', 'used', 'golden_pickaxe') +
                statistic(_~'name', 'used', 'iron_pickaxe') +
                statistic(_~'name', 'used', 'stone_pickaxe') +
                statistic(_~'name', 'used', 'wooden_pickaxe') +

                statistic(_~'name', 'used', 'netherite_hoe') +
                statistic(_~'name', 'used', 'diamond_hoe') +
                statistic(_~'name', 'used', 'golden_hoe') +
                statistic(_~'name', 'used', 'iron_hoe') +
                statistic(_~'name', 'used', 'stone_hoe') +
                statistic(_~'name', 'used', 'wooden_hoe') +

                statistic(_~'name', 'used', 'netherite_shovel') +
                statistic(_~'name', 'used', 'diamond_shovel') +
                statistic(_~'name', 'used', 'golden_shovel') +
                statistic(_~'name', 'used', 'iron_shovel') +
                statistic(_~'name', 'used', 'stone_shovel') +
                statistic(_~'name', 'used', 'wooden_shovel') +

                statistic(_~'name', 'used', 'netherite_axe') +
                statistic(_~'name', 'used', 'diamond_axe') +
                statistic(_~'name', 'used', 'golden_axe') +
                statistic(_~'name', 'used', 'iron_axe') +
                statistic(_~'name', 'used', 'stone_axe') +
                statistic(_~'name', 'used', 'wooden_axe')
            );

            // 更新挖掘榜计分板
            scoreboard('tools_used_all', _~'name', tools_used_score);
        );
       // 更新在线时间
       if (_~'player_type' != 'fake',
            // 获取在线时间（以秒为单位）
            current_time = floor(statistic(_~'name', 'custom', 'play_time') / 20 / 60);
            // 更新在线时长计分板
            scoreboard('online_time', _~'name', current_time);
        );
    
    );

);