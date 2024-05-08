package jetbrains.buildServer.configs.kotlin.buildFeatures

import jetbrains.buildServer.configs.kotlin.*

/**
 * Provides AWS Credentials of chosen AWS Connection.
 *
 * **Example.**
 * Provides AWS Credentials of chosen AWS Connection to the Build Agent
 * ```
 * provideAwsCredentials {
 *   awsConnectionId = "RootAwsConnection"
 *   sessionDuration = "60" //in minutes
 * }
 * ```
 *
 *
 * @see provideAwsCredentials
 */
open class ProvideAwsCredentials() : BuildFeature() {

    init {
        type = "PROVIDE_AWS_CREDS"
    }

    constructor(init: ProvideAwsCredentials.() -> Unit): this() {
        init()
    }

    /**
     * AWS Connection ID.
     *
     *
     * @see AwsConnection
     */
    var awsConnectionId by stringParameter()

    /**
     * Session duration in minutes
     */
    var sessionDuration by stringParameter("awsSessionDuration")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (awsConnectionId == null && !hasParam("awsConnectionId")) {
            consumer.consumePropertyError("awsConnectionId", "mandatory 'awsConnectionId' property is not specified")
        }
    }
}


/**
 *
 * **Example.**
 * Provides AWS Credentials of chosen AWS Connection to the Build Agent
 * ```
 * provideAwsCredentials {
 *   awsConnectionId = "RootAwsConnection"
 *   sessionDuration = "60" //in minutes
 * }
 * ```
 *
 *
 * @see ProvideAwsCredentials
 */
fun BuildFeatures.provideAwsCredentials(init: ProvideAwsCredentials.() -> Unit): ProvideAwsCredentials {
    val result = ProvideAwsCredentials(init)
    feature(result)
    return result
}
