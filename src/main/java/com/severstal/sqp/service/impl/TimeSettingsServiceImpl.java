package com.severstal.sqp.service.impl;

/**
 * Time settings service implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.entity
 */


import com.severstal.sqp.dao.TimeSettingsDao;
import com.severstal.sqp.dto.TimeSettingsDto;
import com.severstal.sqp.entity.TimeSettings;
import com.severstal.sqp.entity.User;
import com.severstal.sqp.service.TimeSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeSettingsServiceImpl implements TimeSettingsService {

    @Autowired
    private TimeSettingsDao timeSettingsDao;

    @Override
    public List<TimeSettings> getTimeSettingsForUser(User user) {
        return timeSettingsDao.getTimeSettingsForUser(user);
    }

    @Override
    public void updateTimeSetting(TimeSettingsDto timeSettingsDto) {
        timeSettingsDao.updateTimeSetting(timeSettingsDto);
    }

    @Override
    public TimeSettings getTimeSettingsById(Long id) {
        return timeSettingsDao.getTimeSettingsById(id);
    }
}
