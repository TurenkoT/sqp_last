package com.severstal.sqp.dao;

/**
 * Time settings dao.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.entity
 */

import com.severstal.sqp.dto.TimeSettingsDto;
import com.severstal.sqp.entity.TimeSettings;
import com.severstal.sqp.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TimeSettingsDao {

    List<TimeSettings> getTimeSettingsForUser(User user);

    void updateTimeSetting(TimeSettingsDto timeSettingsDto);

    TimeSettings getTimeSettingsById(Long id);
}
