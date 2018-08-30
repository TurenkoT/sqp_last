package com.severstal.sqp.service;

/**
 * Time settings service.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.entity
 */


import com.severstal.sqp.dto.TimeSettingsDto;
import com.severstal.sqp.entity.TimeSettings;
import com.severstal.sqp.entity.User;

import java.util.List;

public interface TimeSettingsService {

    List<TimeSettings> getTimeSettingsForUser(User user);

    void updateTimeSetting(TimeSettingsDto timeSettingsDto);

    TimeSettings getTimeSettingsById(Long id);
}
