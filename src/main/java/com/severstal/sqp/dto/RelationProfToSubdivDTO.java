package com.severstal.sqp.dto;

/**
 * Relation Profession to subdivision dto controller.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import lombok.Data;

@Data
public class RelationProfToSubdivDTO {

    String[] profsValues;
    String[] subdivValues;

}
