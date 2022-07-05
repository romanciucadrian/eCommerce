package com.shopme.admin.service.impl;

import com.shopme.admin.setting.GeneralSettingBag;
import com.shopme.common.entity.Setting;

import java.util.List;

public interface ISettingService {

    public List<Setting> listAllSettings();

    public GeneralSettingBag getGeneralSettings();

    public void saveAll(Iterable<Setting> settings);
}
