package com.spacebitlabs.onelock.accessibility

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import timber.log.Timber


class OneLockAccessibiltyService : AccessibilityService() {
    override fun onInterrupt() {
        // do nothing
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (rootInActiveWindow == null) return

        when (event?.eventType) {
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                if (event.source == null || !event.source.isPassword) return

                scanFieldsToFill(rootInActiveWindow, event)
            }
        }
    }

    private fun scanFieldsToFill(rootNode: AccessibilityNodeInfo, event: AccessibilityEvent) {
        val passwordNodes = AccessibilityHelpers.getPasswordNodes(rootNode, event)
        Timber.d("Found ${passwordNodes.size} password nodes")

        if (passwordNodes.size > 0) {
            val uri = AccessibilityHelpers.getUri(rootNode)
            Timber.d("Uri found: $uri")
        }
    }
}

object AccessibilityHelpers {
    fun getPasswordNodes(rootNode: AccessibilityNodeInfo, event: AccessibilityEvent): MutableList<AccessibilityNodeInfo> {
        Timber.d("Getting password nodes from window")
        return getWindowNodes(rootNode, event)
    }

    private fun getWindowNodes(node: AccessibilityNodeInfo, event: AccessibilityEvent, recursionDepth: Int = 0): MutableList<AccessibilityNodeInfo> {
        val nodeList = mutableListOf<AccessibilityNodeInfo>()

        if (recursionDepth >= 50) return nodeList

        val isNodeFromEventSource = node.windowId == event.windowId
        val isNodeFromSystemUi = node.viewIdResourceName?.startsWith(SYSTEM_UI_PACKAGE) ?: false

        if (isNodeFromEventSource && !isNodeFromSystemUi && node.isPassword) {
            nodeList.add(node)
        }

        for (i in 0 until node.childCount) {
            val childNode = node.getChild(i) ?: continue

            if (i > 100) {
                Timber.d("Too many child iterations")
                break
            } else if (childNode.hashCode() == node.hashCode()) {
                Timber.d("Child node is the same as parent node")
            } else {
                nodeList.addAll(getWindowNodes(childNode, event, recursionDepth + 1))
            }
        }

        return nodeList
    }

    fun getUri(root: AccessibilityNodeInfo): String {
        var uri = ANDROID_APP_PROTOCOL + root.packageName
        if (SUPPORTED_BROWSERS.contains(root.packageName)) {
            val browser = SUPPORTED_BROWSERS[root.packageName]!!
            val addressNode = root.findAccessibilityNodeInfosByViewId(
                "${root.packageName}:id/${browser.uriViewId}"
            ).firstOrNull()

            if (addressNode != null) {
                uri = extractUriFromBrowser(uri, addressNode, browser)
            }
        }

        return uri
    }

    fun extractUriFromBrowser(packageUri: String, addressNode: AccessibilityNodeInfo, browser: Browser): String {
        addressNode.text ?: return packageUri

        var uri = addressNode.text.trim().toString()
        if (uri.contains(".")) {
            if (!uri.contains("://") && !uri.contains(" ")) {
                uri = "https://$uri"
            }
        }

        return uri
    }

    private const val SYSTEM_UI_PACKAGE = "com.android.systemui"
    private const val ANDROID_APP_PROTOCOL = "androidapp://"
    private val SUPPORTED_BROWSERS = listOf(
        Browser("com.android.chrome", "url_bar"),
        Browser("com.chrome.beta", "url_bar"),
        Browser("org.chromium.chrome", "url_bar"),
        Browser("com.android.browser", "url"),
        Browser("com.brave.browser", "url_bar"),
        Browser("com.opera.browser", "url_field"),
        Browser("com.opera.browser.beta", "url_field"),
        Browser("com.opera.mini.native", "url_field"),
        Browser("com.opera.touch", "addressbarEdit"),
        Browser("com.chrome.dev", "url_bar"),
        Browser("com.chrome.canary", "url_bar"),
        Browser("com.google.android.apps.chrome", "url_bar"),
        Browser("com.google.android.apps.chrome_dev", "url_bar"),
        Browser("org.codeaurora.swe.browser", "url_bar"),
        Browser("org.iron.srware", "url_bar"),
        Browser("com.sec.android.app.sbrowser", "location_bar_edit_text"),
        Browser("com.sec.android.app.sbrowser.beta", "location_bar_edit_text"),
        Browser("com.yandex.browser", "bro_omnibar_address_title_text"), // TODO some space issue
        Browser("org.mozilla.firefox", "url_bar_title"),
        Browser("org.mozilla.firefox_beta", "url_bar_title"),
        Browser("org.mozilla.fennec_aurora", "url_bar_title"),
        Browser("org.mozilla.fennec_fdroid", "url_bar_title"),
        Browser("org.mozilla.focus", "display_url"),
        Browser("org.mozilla.klar", "display_url"),
        Browser("org.mozilla.fenix", "mozac_browser_toolbar_url_view"),
        Browser("org.mozilla.fenix.nightly", "mozac_browser_toolbar_url_view"),
        Browser("org.mozilla.reference.browser", "mozac_browser_toolbar_url_view"),
        Browser("com.ghostery.android.ghostery", "search_field"),
        Browser("org.adblockplus.browser", "url_bar_title"),
        Browser("com.htc.sense.browser", "title"),
        Browser("com.amazon.cloud9", "url"),
        Browser("mobi.mgeek.TunnyBrowser", "title"),
        Browser("com.nubelacorp.javelin", "enterUrl"),
        Browser("com.jerky.browser2", "enterUrl"),
        Browser("com.mx.browser", "address_editor_with_progress"),
        Browser("com.mx.browser.tablet", "address_editor_with_progress"),
        Browser("com.linkbubble.playstore", "url_text"),
        Browser("com.ksmobile.cb", "address_bar_edit_text"),
        Browser("acr.browser.lightning", "search"),
        Browser("acr.browser.barebones", "search"),
        Browser("com.microsoft.emmx", "url_bar"),
        Browser("com.duckduckgo.mobile.android", "omnibarTextInput"),
        Browser("mark.via.gp", "aw"),
        Browser("org.bromite.bromite", "url_bar"),
        Browser("com.kiwibrowser.browser", "url_bar"),
        Browser("com.ecosia.android", "url_bar"),
        Browser("com.qwant.liberty", "url_bar_title")
    ).map {
        it.packageName to it
    }.toMap()
}