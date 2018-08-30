package com.severstal.sqp.service;

/**
 * upload service interface.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service
 */

import java.util.List;
import java.util.Map;

public interface UploadService {

    Map<Integer, List<String>> parseCSV(String filename);

    List<String> saveCSV(Map<Integer, List<String>> map);

    List<String> saveUsersCSV(Map<Integer, List<String>> map);
}
