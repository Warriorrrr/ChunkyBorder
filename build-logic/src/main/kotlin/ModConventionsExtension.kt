import org.gradle.api.provider.Property

abstract class ModConventionsExtension {
    abstract val target: Property<String>
}
