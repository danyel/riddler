package be.riddler.v1.participant.feature;

import java.util.UUID;

/**
 * UploadPhotoFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface UploadCvFeature {
    void uploadCv(UUID participantId, byte[] data);
}
