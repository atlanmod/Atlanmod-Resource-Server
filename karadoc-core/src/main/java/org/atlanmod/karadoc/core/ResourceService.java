package org.atlanmod.karadoc.core;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import java.util.List;

public interface ResourceService {

    /**
     * Find a model based on the uri
     * @param modelUri model uri
     * @return the model
     */
    Response<Resource> getModel(String modelUri);


    /**
     * Get all the resource in the resource set
     * @return the List of all resource
     */
    Response<List<Resource>> getAll();

    /**
     * Get all the model and return there uri
     *
     * @return A list of all the models uri
     */
    Response<List<String>> getModelUris();

    /**
     * Get element by name
     * @param modelUri The uri of the resource
     * @param elementname name of the object
     * @return the object found. Null of none
     */
    Response<EObject> getModelElementByName(String modelUri, String elementname);


    /**
     * Delete a model
     * @param modelUri the id the model to delete
     * @return a boolean true if the removal succeeded.
     */
    Response<Boolean> delete(String modelUri);

    /**
     * Close a model wihout saving it's content to the disk
     * @param modelUri model to close
     * @return a boolean representing the status of the operation
     */
    Response<Boolean> close(String modelUri);

    /**
     * Create a new model
     * @param modelUri the uri of the new model
     * @param modelAsJSON the new model to create as a json string. It must be an instance of the loaded metamodel
     * @return a boolean true if the model was added.
     */
    Response<Boolean> create(String modelUri, String modelAsJSON);

    /**
     * Replace a model with the new given model. Throw a runtime error if no model to replace is found
     * @param modelUri the URI of the model to replace
     * @param updatedModel the model tu use instead as a json string
     * @return a boolean true if the operation succeeded
     */
    Response<Boolean> update(String modelUri, String updatedModel);

    /**
     * Save model to server disk
     *
     * @param modelUri the uri of the model to save
     */
    Response<Boolean> save(String modelUri) ;

    /**
     * Save all the models in the resourceset
     * @return a boolean true if all the models were saved.
     */
    Response<Boolean> saveAll();


//    /**
//     *
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
    Response<Boolean> ping();

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

    // Todo: implement this
   // boolean send(String modelUri, String message);

    /**
     * Unsubscribe from model
     * @param modelUri the model to unsubscribe
     * @return a boolean true if the operation succeeded
     */
    Response<Boolean> unsubscribe(String modelUri);

    /**
     * Undo last modification on the model
     * @param modelUri the uri of the model on witch to perform the undo
     * @return the modified resource
     */
    Response<Resource> undo(String modelUri);

    /**
     * Redo last modification on the model
     * @param modelUri the uri of the model on witch to perform the undo
     * @return the modified resource
     */
    Response<Resource> redo(String modelUri);
}
