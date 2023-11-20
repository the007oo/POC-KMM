import UIKit
import SwiftUI
import ComposeApp

class SwiftUIInUIView<Content: View>: UIView {
    init(content: Content) {
        super.init(frame: CGRect())
        let hostingController = UIHostingController(rootView: content)
        hostingController.view.translatesAutoresizingMaskIntoConstraints = false
        addSubview(hostingController.view)
        NSLayoutConstraint.activate([
            hostingController.view.topAnchor.constraint(equalTo: topAnchor),
            hostingController.view.leadingAnchor.constraint(equalTo: leadingAnchor),
            hostingController.view.trailingAnchor.constraint(equalTo: trailingAnchor),
            hostingController.view.bottomAnchor.constraint(equalTo: bottomAnchor)
        ])
    }
    
    required init?(coder aDecoder: NSCoder) {
            fatalError("init(coder:) has not been implemented")
        }
}

struct ComposeView: UIViewControllerRepresentable {
    @State var message: String = ""
    @State var messageFromKotlin: String = ""
    
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.ComposeEntryPointWithUIView(createUIView: { callback in
            SwiftUIInUIView(
                    content: VStack {
                        TextField("SwiftUI TextField",text: $message).padding().background(Color(uiColor:.lightGray))
                        
                        Button("SwiftUI Button") {
                            let _ = callback(message)
                        }
                        .buttonStyle(.borderedProminent)
                                            
                        MessageFromKotlinTextView(messageFromKotlin: $messageFromKotlin)
                    }.padding(10)
                )
        },
        onButtonClick: { message in
            messageFromKotlin = message
        })
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        print("updateUIViewController \(messageFromKotlin)")
    }
}

struct MessageFromKotlinTextView: View {
    @Binding var messageFromKotlin: String

    var body: some View {
        if !messageFromKotlin.isEmpty{
            Text("Message from kotlin \(messageFromKotlin)")
        }
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView().ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



