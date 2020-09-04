package com.mud.game.worldrun.db.repository;

import com.mud.game.object.typeclass.MailObject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MailObjectRepository extends MongoRepository<MailObject, String> {
    List<MailObject> findMailObjectListByRecipientId(String recipientId);

    List<MailObject> findMailObjectByInitiatorId(String id);

    MailObject findMailObjecByIdAndRecipientId(String mailId, String id);

    MailObject findMailObjecById(String mailId);
}
