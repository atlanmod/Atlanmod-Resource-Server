package org.atlanmod.karadoc.core;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import java.io.IOException;
import java.util.List;

public interface ResourceService {

    /**
     * Find a model based on the uri
     * @param modelUri model uri
     * @return the model
     */
    Resource getModel(String modelUri);

    //Todo: remove because we everything is serialized to json
    //EObject get(String modelUri, String format);

    /**
     * Get all the resource in the resource set
     * @return the List of all resource
     */
    List<Resource> getAll();

    //Todo: remove because we everything is serialized to json
    //EList<Resource> getAll(String format);

    /**
     * Get all the model and return there uri
     *
     * @return A list of all the models uri
     */
    List<String> getModelUris();

    //Does not exist on our model. Use uri instead
    //EObject getModelElementById(String modelUri, String elementid);

    //Does not exist in our model. Use uri instead
    //EObject getModelElementById(String modelUri, String elementid, String format);

    /**
     * Get element by name
     * @param modelUri The uri of the resource
     * @param elementname name of the object
     * @return the object found. Null of none
     */
    EObject getModelElementByName(String modelUri, String elementname);


    /**
     * Delete a model
     * @param modelUri the id the model to delete
     * @return a boolean true if the removal succeeded.
     */
    boolean delete(String modelUri);

    /**
     * FIXME i have no idea what is supposed to do
     * @param modelUri
     * @return
     */
    boolean close(String modelUri);

    /**
     * Create a new model
     * @param modelUri the uri of the new model
     * @param model the new model to create. It must be an instance of the loaded metamodel
     * @return a boolean true if the model was added.
     */
    boolean create(String modelUri, String modelAsJSON);

    //Todo remove because we everything is serialized to json
    //Resource update(String modelUri, String updatedModelAsJsonText);



    // Todo remove because we everything is serialized to json
    //boolean create(String modelUri, Resource createdModel, String format);
    Resource update(String modelUri, Resource updatedModel);

    /**
     * Save model to server disk
     *
     * @param modelUri the uri of the model to save
     */
    void save(String modelUri) throws IOException;

    /**
     * Save all the model (aka the resourceSet)
     * @return a boolean true if all the models were saved.
     */
    boolean saveAll();

    //Todo: Find out what this is supposed to do o_o
//    /**
//     * ?????
//     * @param modelUri the uri of the model to validate
//     * @return yes
//     */
//    boolean validate(String modelUri);
//
//    boolean getValidationConstraints(String modelUri);
//
//    Resource getTypeSchema(String modelUri);
//
//    Resource getUiSchema(String schemaName);

    //Todo: Not supported yet.
    //CompletableFuture<Response<Boolean>> configure(ServerConfiguration configuration);


    /**
     * Ping the model
     * @return pong
     */
    boolean ping();

    //CompletableFuture<Response<String>> edit(String modelUri, CCommand command, String format);

    //CompletableFuture<Response<String>> edit(String modelUri, ArrayNode jsonPatch, String format);

//TODO find a way to implement subscriber

//    void subscribe(String modelUri, SubscriptionListener subscriptionListener);
//
//    void subscribe(String modelUri, SubscriptionListener subscriptionListener, String format);
//
//    void subscribe(String modelUri, SubscriptionListener subscriptionListener, long timeout);
//
//    void subscribe(String modelUri, SubscriptionListener subscriptionListener, String format, long timeout);
//
//    void subscribeWithValidation(String modelUri, SubscriptionListener subscriptionListener);
//
//    void subscribeWithValidation(String modelUri, SubscriptionListener subscriptionListener, String format);
//
//    void subscribeWithValidation(String modelUri, SubscriptionListener subscriptionListener, long timeout);
//
//    void subscribeWithValidation(String modelUri, SubscriptionListener subscriptionListener, String format,
//                                 long timeout);
//
//    void subscribe(String modelUri, SubscriptionListener subscriptionListener, SubscriptionOptions options);

    // Todo: remove this, i don't know what this is supposed to do.
   // boolean send(String modelUri, String message);

    /**
     * Unsubscribe from model
     * @param modelUri the model to unsubscribe
     * @return a boolean true if the operation succeeded
     */
    boolean unsubscribe(String modelUri);

    /**
     * Undo last modification on the model
     * @param modelUri the uri of the model on witch to perform the undo
     * @return the modified resource
     */
    Resource undo(String modelUri);

    /**
     * Redo last modification on the model
     * @param modelUri the uri of the model on witch to perform the undo
     * @return the modified resource
     */
    Resource redo(String modelUri);
}
