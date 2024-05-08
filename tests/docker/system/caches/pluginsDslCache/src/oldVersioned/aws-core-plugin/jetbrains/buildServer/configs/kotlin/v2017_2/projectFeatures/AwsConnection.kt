package jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2017_2.*

/**
 * Stores information and configuration for the access to Amazon Web Services.
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to store and manage AWS Credentials (IAM User Access Key).
 * When using Session Credentials, a new temporary Access Key will be generated each time this connection is used.
 * ```
 * awsConnection {
 *   id = "AwsIamUser"
 *   name = "AWS IAM User"
 *   regionName = "eu-central-1"
 *   credentialsType = static {
 *     accessKeyId = "keyId"
 *     secretAccessKey = "Link to credentialsJSON property containing AWS secret access key"
 *     useSessionCredentials = true
 *     stsEndpoint = "https://sts.eu-central-1.amazonaws.com"
 *   }
 * }
 * ```
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to store and manage AWS Credentials (IAM User Access Key).
 * This connection can be used by subprojects.
 * ```
 * awsConnection {
 *   id = "AwsIamUser"
 *   name = "AWS IAM User"
 *   regionName = "eu-central-1"
 *   credentialsType = static {
 *     accessKeyId = "keyId"
 *     secretAccessKey = "Link to credentialsJSON property containing AWS secret access key"
 *   }
 *   allowInSubProjects = true
 * }
 * ```
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to store and manage AWS Credentials (IAM User Access Key).
 * This connection can be used in build steps.
 * ```
 * awsConnection {
 *   id = "AwsIamUser"
 *   name = "AWS IAM User"
 *   regionName = "eu-central-1"
 *   credentialsType = static {
 *     accessKeyId = "keyId"
 *     secretAccessKey = "Link to credentialsJSON property containing AWS secret access key"
 *   }
 *   allowInBuilds = true
 * }
 * ```
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to assume an AWS IAM Role using another AWS Connection.
 * ```
 * awsConnection {
 *   id = "AwsIamRole"
 *   name = "AWS IAM Role"
 *   regionName = "eu-central-1"
 *   credentialsType = iamRole {
 *     roleArn = "arn:aws:iam::account:role/role-name-with-path"
 *     sessionName = "TeamCity-session-identifier"
 *     awsConnectionId = "AwsIamUser"
 *     stsEndpoint = "https://sts.eu-central-1.amazonaws.com"
 *   }
 * }
 * ```
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to look for credentials in the default locations on the TeamCity server machine using the Default Credentials Provider Chain.
 * ```
 * awsConnection {
 *   id = "AwsDefaultCredsProviderChain"
 *   name = "AWS Default Creds Provider Chain"
 *   regionName = "eu-central-1"
 *   credentialsType = default()
 * }
 * ```
 *
 *
 * @see awsConnection
 */
open class AwsConnection() : ProjectFeature() {

    init {
        type = "OAuthProvider"
        param("providerType", "AWS")
    }

    constructor(init: AwsConnection.() -> Unit): this() {
        init()
    }

    /**
     * AWS connection display name
     */
    var name by stringParameter("displayName")

    /**
     * Region where this connection will be used.
     */
    var regionName by stringParameter("awsRegionName")

    /**
     * Custom identifier for this AWS Connection
     */
    var projectFeatureId by stringParameter()

    /**
     * The way how to obtain credentials (just provide the keys, assume IAM role or other)
     */
    var credentialsType by compoundParameter<CredentialsType>("awsCredentialsType")

    sealed class CredentialsType(value: String? = null): CompoundParam<CredentialsType>(value) {
        abstract fun validate(consumer: ErrorConsumer)

        class Static() : CredentialsType("awsAccessKeys") {

            /**
             * Access Key ID
             */
            var accessKeyId by stringParameter("awsAccessKeyId")

            /**
             * Secret Access Key
             */
            var secretAccessKey by stringParameter("secure:awsSecretAccessKey")

            /**
             * Whether to use temporary credentials provided by STS service
             */
            var useSessionCredentials by booleanParameter("awsSessionCredentials")

            /**
             * Endpoint from where to obtain session credentials
             */
            var stsEndpoint by stringParameter("awsStsEndpoint")

            override fun validate(consumer: ErrorConsumer) {
                if (accessKeyId == null && !hasParam("awsAccessKeyId")) {
                    consumer.consumePropertyError("credentialsType.accessKeyId", "mandatory 'credentialsType.accessKeyId' property is not specified")
                }
                if (secretAccessKey == null && !hasParam("secure:awsSecretAccessKey")) {
                    consumer.consumePropertyError("credentialsType.secretAccessKey", "mandatory 'credentialsType.secretAccessKey' property is not specified")
                }
            }
        }

        class IamRole() : CredentialsType("awsAssumeIamRole") {

            /**
             * AWS IAM Role ARN
             */
            var roleArn by stringParameter("awsIamRoleArn")

            /**
             * An identifier for the assumed role session. Use the role session name to uniquely identify a session when the same role is assumed by different principals or for different reasons.
             */
            var sessionName by stringParameter("awsIamRoleSessionName")

            /**
             * Principal AWS Connection ID (Who will assume the IAM Role)
             */
            var awsConnectionId by stringParameter()

            /**
             * Endpoint from where to obtain session credentials of the assumed IAM Role
             */
            var stsEndpoint by stringParameter("awsStsEndpoint")

            override fun validate(consumer: ErrorConsumer) {
                if (roleArn == null && !hasParam("awsIamRoleArn")) {
                    consumer.consumePropertyError("credentialsType.roleArn", "mandatory 'credentialsType.roleArn' property is not specified")
                }
            }
        }

        class Default() : CredentialsType("defaultProvider") {

            override fun validate(consumer: ErrorConsumer) {
            }
        }
    }

    fun static(init: CredentialsType.Static.() -> Unit = {}) : CredentialsType.Static {
        val result = CredentialsType.Static()
        result.init()
        return result
    }

    /**
     * Uses another (principal) AWS connection to assume an IAM Role with its permissions. Please, note that the principal connection should have rights to assume the role, more: https://docs.aws.amazon.com/workdocs/latest/developerguide/wd-iam-grantdev.html
     */
    fun iamRole(init: CredentialsType.IamRole.() -> Unit = {}) : CredentialsType.IamRole {
        val result = CredentialsType.IamRole()
        result.init()
        return result
    }

    /**
     * Looks for credentials in this order:
     * Env Vars - AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY
     * Java System Properties - aws.accessKeyId and aws.secretKey
     * Web Identity Token credentials from the environment or container
     * Credential profiles file at the default location (~/.aws/credentials)
     * Credentials delivered through the Amazon EC2 container service if AWS_CONTAINER_CREDENTIALS_RELATIVE_URI" environment variable is set and security manager has permission to access the variable,
     * Instance profile credentials delivered through the Amazon EC2 metadata service
     * more: https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
     */
    fun default() = CredentialsType.Default()

    /**
     * Whether sub-projects can utilize the connection or not
     */
    var allowInSubProjects by booleanParameter("awsAllowedInSubProjects")

    /**
     * Whether build steps can utilize the connection or not
     */
    var allowInBuilds by booleanParameter("awsAllowedInBuilds")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        credentialsType?.validate(consumer)
    }
}


/**
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to store and manage AWS Credentials (IAM User Access Key).
 * When using Session Credentials, a new temporary Access Key will be generated each time this connection is used.
 * ```
 * awsConnection {
 *   id = "AwsIamUser"
 *   name = "AWS IAM User"
 *   regionName = "eu-central-1"
 *   credentialsType = static {
 *     accessKeyId = "keyId"
 *     secretAccessKey = "Link to credentialsJSON property containing AWS secret access key"
 *     useSessionCredentials = true
 *     stsEndpoint = "https://sts.eu-central-1.amazonaws.com"
 *   }
 * }
 * ```
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to store and manage AWS Credentials (IAM User Access Key).
 * This connection can be used by subprojects.
 * ```
 * awsConnection {
 *   id = "AwsIamUser"
 *   name = "AWS IAM User"
 *   regionName = "eu-central-1"
 *   credentialsType = static {
 *     accessKeyId = "keyId"
 *     secretAccessKey = "Link to credentialsJSON property containing AWS secret access key"
 *   }
 *   allowInSubProjects = true
 * }
 * ```
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to store and manage AWS Credentials (IAM User Access Key).
 * This connection can be used in build steps.
 * ```
 * awsConnection {
 *   id = "AwsIamUser"
 *   name = "AWS IAM User"
 *   regionName = "eu-central-1"
 *   credentialsType = static {
 *     accessKeyId = "keyId"
 *     secretAccessKey = "Link to credentialsJSON property containing AWS secret access key"
 *   }
 *   allowInBuilds = true
 * }
 * ```
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to assume an AWS IAM Role using another AWS Connection.
 * ```
 * awsConnection {
 *   id = "AwsIamRole"
 *   name = "AWS IAM Role"
 *   regionName = "eu-central-1"
 *   credentialsType = iamRole {
 *     roleArn = "arn:aws:iam::account:role/role-name-with-path"
 *     sessionName = "TeamCity-session-identifier"
 *     awsConnectionId = "AwsIamUser"
 *     stsEndpoint = "https://sts.eu-central-1.amazonaws.com"
 *   }
 * }
 * ```
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to look for credentials in the default locations on the TeamCity server machine using the Default Credentials Provider Chain.
 * ```
 * awsConnection {
 *   id = "AwsDefaultCredsProviderChain"
 *   name = "AWS Default Creds Provider Chain"
 *   regionName = "eu-central-1"
 *   credentialsType = default()
 * }
 * ```
 *
 *
 * @see AwsConnection
 */
fun ProjectFeatures.awsConnection(init: AwsConnection.() -> Unit): AwsConnection {
    val result = AwsConnection(init)
    feature(result)
    return result
}
