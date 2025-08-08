package com.example.kindspark.data

import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class KindnessRepository(private val dao: KindnessPromptDao) {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // Pre-defined kindness prompts
    private val defaultPrompts = listOf(
        KindnessPrompt(1, "Compliment someone genuinely.", "social"),
        KindnessPrompt(2, "Call or text a family member to check on them.", "family"),
        KindnessPrompt(3, "Help a stranger today in any small way.", "community"),
        KindnessPrompt(4, "Leave a positive comment on social media.", "digital"),
        KindnessPrompt(5, "Pick up litter you see on the ground.", "environment"),
        KindnessPrompt(6, "Support a local business with kind words or a review.", "community"),
        KindnessPrompt(7, "Hold the door open for someone.", "courtesy"),
        KindnessPrompt(8, "Write a short thank-you note to someone.", "gratitude"),
        KindnessPrompt(9, "Donate clothes or items you no longer need.", "charity"),
        KindnessPrompt(10, "Smile at a stranger and say hello.", "social"),
        KindnessPrompt(11, "Let someone go ahead of you in line.", "courtesy"),
        KindnessPrompt(12, "Offer to help a coworker with their tasks.", "workplace"),
        KindnessPrompt(13, "Send an encouraging message to a friend.", "friendship"),
        KindnessPrompt(14, "Pay for someone's coffee or meal.", "generosity"),
        KindnessPrompt(15, "Volunteer for a local charity or cause.", "community"),
        KindnessPrompt(16, "Listen actively to someone who needs to talk.", "empathy"),
        KindnessPrompt(17, "Forgive someone who has wronged you.", "forgiveness"),
        KindnessPrompt(18, "Share your knowledge or skills with others.", "teaching"),
        KindnessPrompt(19, "Give a genuine compliment to a service worker.", "appreciation"),
        KindnessPrompt(20, "Plant a flower or tree in your community.", "environment"),
        KindnessPrompt(21, "Compliment a coworker on their effort.", "workplace"),
        KindnessPrompt(22, "Offer to carry groceries for someone.", "courtesy"),
        KindnessPrompt(23, "Share an inspiring article with a friend.", "digital"),
        KindnessPrompt(24, "Water a neighbor's plants while they're away.", "community"),
        KindnessPrompt(25, "Bring homemade snacks to share at work.", "generosity"),
        KindnessPrompt(26, "Donate to an animal shelter.", "charity"),
        KindnessPrompt(27, "Offer to babysit for a tired parent.", "community"),
        KindnessPrompt(28, "Tell someone you appreciate their hard work.", "appreciation"),
        KindnessPrompt(29, "Recommend a good book to someone.", "friendship"),
        KindnessPrompt(30, "Give an honest, positive review online.", "digital"),
        KindnessPrompt(31, "Support a fundraiser for a good cause.", "charity"),
        KindnessPrompt(32, "Send flowers to someone unexpectedly.", "generosity"),
        KindnessPrompt(33, "Help someone carry heavy bags.", "courtesy"),
        KindnessPrompt(34, "Offer your seat to someone who needs it more.", "courtesy"),
        KindnessPrompt(35, "Encourage a child in their studies.", "teaching"),
        KindnessPrompt(36, "Share an uplifting story on social media.", "digital"),
        KindnessPrompt(37, "Bring coffee to a colleague.", "generosity"),
        KindnessPrompt(38, "Hold the elevator for someone rushing.", "courtesy"),
        KindnessPrompt(39, "Thank your bus driver or taxi driver.", "gratitude"),
        KindnessPrompt(40, "Feed birds or stray animals safely.", "environment"),
        KindnessPrompt(41, "Teach someone a skill they want to learn.", "teaching"),
        KindnessPrompt(42, "Pick up trash at a local park.", "environment"),
        KindnessPrompt(43, "Offer to take a photo for tourists.", "courtesy"),
        KindnessPrompt(44, "Help a neighbor with gardening.", "community"),
        KindnessPrompt(45, "Give someone a handwritten card.", "gratitude"),
        KindnessPrompt(46, "Share leftovers with someone in need.", "charity"),
        KindnessPrompt(47, "Offer to proofread a friend’s resume.", "workplace"),
        KindnessPrompt(48, "Encourage someone chasing a goal.", "empathy"),
        KindnessPrompt(49, "Gift a plant to brighten someone’s day.", "generosity"),
        KindnessPrompt(50, "Leave coins in a vending machine.", "generosity"),
        KindnessPrompt(51, "Help someone cross the street.", "courtesy"),
        KindnessPrompt(52, "Bake cookies for your neighbors.", "community"),
        KindnessPrompt(53, "Return a lost item to its owner.", "courtesy"),
        KindnessPrompt(54, "Share your umbrella on a rainy day.", "courtesy"),
        KindnessPrompt(55, "Give away books you’ve finished reading.", "charity"),
        KindnessPrompt(56, "Check in on an elderly neighbor.", "community"),
        KindnessPrompt(57, "Write a thank-you email to a teacher.", "gratitude"),
        KindnessPrompt(58, "Help set up or clean after an event.", "community"),
        KindnessPrompt(59, "Offer a sincere apology when wrong.", "forgiveness"),
        KindnessPrompt(60, "Speak kindly about someone behind their back.", "empathy"),
        KindnessPrompt(61, "Share a healthy recipe with a friend.", "friendship"),
        KindnessPrompt(62, "Let someone merge into your lane in traffic.", "courtesy"),
        KindnessPrompt(63, "Invite a new neighbor over for tea.", "community"),
        KindnessPrompt(64, "Offer to walk a friend’s dog.", "community"),
        KindnessPrompt(65, "Give an uplifting compliment to a stranger.", "social"),
        KindnessPrompt(66, "Donate school supplies to children in need.", "charity"),
        KindnessPrompt(67, "Encourage someone to follow their dreams.", "empathy"),
        KindnessPrompt(68, "Bring a snack for a coworker working late.", "workplace"),
        KindnessPrompt(69, "Help a student with their homework.", "teaching"),
        KindnessPrompt(70, "Plant flowers in a public space.", "environment"),
        KindnessPrompt(71, "Share your knowledge in an online forum.", "teaching"),
        KindnessPrompt(72, "Say good morning to everyone you meet.", "social"),
        KindnessPrompt(73, "Compliment someone’s outfit or style.", "social"),
        KindnessPrompt(74, "Leave a kind note in a library book.", "gratitude"),
        KindnessPrompt(75, "Offer to take someone’s shift at work.", "workplace"),
        KindnessPrompt(76, "Send a postcard to a friend.", "friendship"),
        KindnessPrompt(77, "Offer your place in a queue to someone.", "courtesy"),
        KindnessPrompt(78, "Help a tourist with directions.", "courtesy"),
        KindnessPrompt(79, "Gift a homemade craft to someone.", "generosity"),
        KindnessPrompt(80, "Organize a small charity event.", "charity"),
        KindnessPrompt(81, "Pay for extra time on someone’s parking meter.", "generosity"),
        KindnessPrompt(82, "Share motivational quotes online.", "digital"),
        KindnessPrompt(83, "Help clean a shared workspace.", "workplace"),
        KindnessPrompt(84, "Send a care package to someone far away.", "friendship"),
        KindnessPrompt(85, "Compliment a child’s creativity.", "empathy"),
        KindnessPrompt(86, "Support a friend’s small business.", "community"),
        KindnessPrompt(87, "Give a stranger directions kindly.", "courtesy"),
        KindnessPrompt(88, "Help pack donations at a shelter.", "charity"),
        KindnessPrompt(89, "Encourage someone having a bad day.", "empathy"),
        KindnessPrompt(90, "Refill someone’s water bottle.", "courtesy"),
        KindnessPrompt(91, "Send a message to someone you miss.", "friendship"),
        KindnessPrompt(92, "Donate blankets to a homeless shelter.", "charity"),
        KindnessPrompt(93, "Help someone find a lost pet.", "community"),
        KindnessPrompt(94, "Smile and greet your cashier.", "social"),
        KindnessPrompt(95, "Organize a group to clean the beach.", "environment"),
        KindnessPrompt(96, "Share seeds with a neighbor for gardening.", "environment"),
        KindnessPrompt(97, "Bring a small treat for your teacher.", "gratitude"),
        KindnessPrompt(98, "Encourage someone learning a new hobby.", "teaching"),
        KindnessPrompt(99, "Offer to fix something for a friend.", "community"),
        KindnessPrompt(100, "Buy extra groceries for someone in need.", "charity"),
        KindnessPrompt(101, "Teach a friend a helpful tech skill.", "teaching"),
        KindnessPrompt(102, "Offer a free ride to someone in need.", "courtesy"),
        KindnessPrompt(103, "Send a positive text to a coworker.", "workplace"),
        KindnessPrompt(104, "Help a neighbor shovel snow.", "community"),
        KindnessPrompt(105, "Share fruit from your garden.", "generosity"),
        KindnessPrompt(106, "Praise someone’s kindness publicly.", "appreciation"),
        KindnessPrompt(107, "Support a crowdfunding campaign.", "charity"),
        KindnessPrompt(108, "Make a playlist for a friend.", "friendship"),
        KindnessPrompt(109, "Bring lunch for someone.", "generosity"),
        KindnessPrompt(110, "Leave a kind comment on a blog.", "digital"),
        KindnessPrompt(111, "Help a child with a creative project.", "teaching"),
        KindnessPrompt(112, "Hold the gate open for someone.", "courtesy"),
        KindnessPrompt(113, "Send appreciation to a public service worker.", "gratitude"),
        KindnessPrompt(114, "Donate toys to a children’s hospital.", "charity"),
        KindnessPrompt(115, "Offer to read to someone who can’t see well.", "empathy"),
        KindnessPrompt(116, "Post a thank-you on social media.", "digital"),
        KindnessPrompt(117, "Help set up chairs for an event.", "community"),
        KindnessPrompt(118, "Invite a colleague for lunch.", "workplace"),
        KindnessPrompt(119, "Compliment someone’s cooking.", "appreciation"),
        KindnessPrompt(120, "Plant herbs to share with neighbors.", "environment"),
        KindnessPrompt(121, "Offer to help someone move their furniture.", "community"),
        KindnessPrompt(122, "Write an inspiring message on a sticky note and leave it somewhere public.", "gratitude"),
        KindnessPrompt(123, "Share an educational video with a young learner.", "teaching"),
        KindnessPrompt(124, "Help someone assemble flat-pack furniture.", "courtesy"),
        KindnessPrompt(125, "Donate blood at your local donation center.", "charity"),
        KindnessPrompt(126, "Organize a community swap meet for reusable goods.", "environment"),
        KindnessPrompt(127, "Offer to tutor a student for free.", "teaching"),
        KindnessPrompt(128, "Compliment a parent on their child’s good behavior.", "appreciation"),
        KindnessPrompt(129, "Help carry a stroller up or down stairs.", "courtesy"),
        KindnessPrompt(130, "Share your favorite recipe with a friend.", "friendship"),
        KindnessPrompt(131, "Sponsor a child’s education or school supplies.", "charity"),
        KindnessPrompt(132, "Help someone set up their new phone or computer.", "workplace"),
        KindnessPrompt(133, "Give extra change to someone short on bus fare.", "generosity"),
        KindnessPrompt(134, "Start a donation jar for a colleague in need.", "workplace"),
        KindnessPrompt(135, "Encourage a small artist or creator by purchasing their work.", "community"),
        KindnessPrompt(136, "Send a book to someone who loves reading.", "friendship"),
        KindnessPrompt(137, "Offer to clean up after a party.", "community"),
        KindnessPrompt(138, "Write a letter to a soldier or first responder.", "gratitude"),
        KindnessPrompt(139, "Help translate for someone who doesn’t speak the local language.", "courtesy"),
        KindnessPrompt(140, "Deliver fresh fruit to a neighbor.", "generosity"),
        KindnessPrompt(141, "Donate old blankets to an animal shelter.", "charity"),
        KindnessPrompt(142, "Offer to water office plants while someone is away.", "workplace"),
        KindnessPrompt(143, "Bring treats to your child’s school staff.", "gratitude"),
        KindnessPrompt(144, "Share a positive news story online.", "digital"),
        KindnessPrompt(145, "Give away unused art or craft supplies.", "charity"),
        KindnessPrompt(146, "Offer to mow a neighbor’s lawn.", "community"),
        KindnessPrompt(147, "Leave a generous tip for excellent service.", "generosity"),
        KindnessPrompt(148, "Compliment someone’s handwriting.", "social"),
        KindnessPrompt(149, "Buy a reusable bag for someone shopping.", "environment"),
        KindnessPrompt(150, "Help organize a recycling drive.", "environment"),
        KindnessPrompt(151, "Send a surprise coffee delivery to a friend.", "generosity"),
        KindnessPrompt(152, "Offer to run errands for someone unwell.", "community"),
        KindnessPrompt(153, "Donate sports equipment to a youth team.", "charity"),
        KindnessPrompt(154, "Share an uplifting podcast episode.", "digital"),
        KindnessPrompt(155, "Teach a child a simple cooking recipe.", "teaching"),
        KindnessPrompt(156, "Help decorate for a celebration.", "community"),
        KindnessPrompt(157, "Compliment someone’s voice or speaking style.", "appreciation"),
        KindnessPrompt(158, "Offer to clean someone’s car for free.", "generosity"),
        KindnessPrompt(159, "Participate in a community blood drive.", "charity"),
        KindnessPrompt(160, "Share career advice with a junior colleague.", "workplace"),
        KindnessPrompt(161, "Bring fresh flowers to a hospital patient.", "generosity"),
        KindnessPrompt(162, "Share your Wi-Fi hotspot with someone in need.", "courtesy"),
        KindnessPrompt(163, "Help repair a community bench or fixture.", "environment"),
        KindnessPrompt(164, "Send a thank-you card to a coach or mentor.", "gratitude"),
        KindnessPrompt(165, "Offer to help with yard work for someone elderly.", "community"),
        KindnessPrompt(166, "Buy snacks for delivery drivers.", "generosity"),
        KindnessPrompt(167, "Teach someone how to use a budgeting app.", "teaching"),
        KindnessPrompt(168, "Organize a group hike or outdoor activity.", "friendship"),
        KindnessPrompt(169, "Give fresh baked bread to a neighbor.", "generosity"),
        KindnessPrompt(170, "Help a friend apply for a job online.", "workplace"),
        KindnessPrompt(171, "Offer to be a study buddy for a student.", "teaching"),
        KindnessPrompt(172, "Donate gently used kitchenware to a shelter.", "charity"),
        KindnessPrompt(173, "Compliment a stranger’s unique accessory.", "social"),
        KindnessPrompt(174, "Help clean an office fridge or break room.", "workplace"),
        KindnessPrompt(175, "Gift a puzzle or board game to a family.", "generosity"),
        KindnessPrompt(176, "Take photos at a community event and share them.", "community"),
        KindnessPrompt(177, "Sponsor a public space beautification project.", "environment"),
        KindnessPrompt(178, "Give a friend a ride to the airport.", "courtesy"),
        KindnessPrompt(179, "Share language-learning resources with someone.", "teaching"),
        KindnessPrompt(180, "Bring homemade soup to someone sick.", "generosity"),
        KindnessPrompt(181, "Offer to help paint a community building.", "community"),
        KindnessPrompt(182, "Post an encouraging message on a community board.", "digital"),
        KindnessPrompt(183, "Help someone fix a bicycle.", "community"),
        KindnessPrompt(184, "Gift seeds to someone starting a garden.", "environment"),
        KindnessPrompt(185, "Offer to pet-sit while someone is away.", "community"),
        KindnessPrompt(186, "Compliment someone’s problem-solving skills.", "appreciation"),
        KindnessPrompt(187, "Share a free eBook with a friend.", "digital"),
        KindnessPrompt(188, "Bring extra pens and stationery for colleagues.", "workplace"),
        KindnessPrompt(189, "Offer to carry a heavy package for someone.", "courtesy"),
        KindnessPrompt(190, "Help organize a local food pantry.", "charity"),
        KindnessPrompt(191, "Invite someone sitting alone to join you.", "social"),
        KindnessPrompt(192, "Give someone a framed photo as a gift.", "generosity"),
        KindnessPrompt(193, "Offer to pick up someone’s dry cleaning.", "community"),
        KindnessPrompt(194, "Share healthy snacks at work.", "workplace"),
        KindnessPrompt(195, "Offer to guide a tourist through a landmark.", "courtesy"),
        KindnessPrompt(196, "Donate unused musical instruments.", "charity"),
        KindnessPrompt(197, "Start a ‘kindness chain’ in your workplace.", "workplace"),
        KindnessPrompt(198, "Help remove graffiti from a wall.", "environment"),
        KindnessPrompt(199, "Send a care package to a college student.", "friendship"),
        KindnessPrompt(200, "Compliment someone’s dedication to their work.", "appreciation"),
        KindnessPrompt(201, "Volunteer to help at a charity event.", "charity"),
        KindnessPrompt(202, "Give a child free art supplies.", "generosity"),
        KindnessPrompt(203, "Offer to proofread a friend’s writing project.", "friendship"),
        KindnessPrompt(204, "Help organize a neighborhood potluck.", "community"),
        KindnessPrompt(205, "Compliment a stranger’s hairstyle.", "social"),
        KindnessPrompt(206, "Gift a reusable coffee cup to a friend.", "environment"),
        KindnessPrompt(207, "Offer to help with technical issues during an event.", "workplace"),
        KindnessPrompt(208, "Share career opportunities with someone job-hunting.", "workplace"),
        KindnessPrompt(209, "Deliver groceries to someone recovering from illness.", "generosity"),
        KindnessPrompt(210, "Plant a tree in memory of someone.", "environment"),
        KindnessPrompt(211, "Help set up chairs for a public event.", "community"),
        KindnessPrompt(212, "Compliment a teacher on their impact.", "gratitude"),
        KindnessPrompt(213, "Offer to guide someone through a new city.", "courtesy"),
        KindnessPrompt(214, "Leave positive feedback for a freelancer online.", "digital"),
        KindnessPrompt(215, "Donate unused bedding to a shelter.", "charity"),
        KindnessPrompt(216, "Encourage a friend to take a needed break.", "empathy"),
        KindnessPrompt(217, "Offer to drive someone to an important appointment.", "courtesy"),
        KindnessPrompt(218, "Gift a cookbook to a food enthusiast.", "friendship"),
        KindnessPrompt(219, "Help repaint playground equipment.", "environment"),
        KindnessPrompt(220, "Compliment a stranger’s sense of humor.", "social"),
        KindnessPrompt(221, "Offer to take group photos for people at a gathering.", "courtesy"),
        KindnessPrompt(222, "Leave a kind anonymous note for a colleague.", "gratitude"),
        KindnessPrompt(223, "Organize a coat drive during winter.", "charity"),
        KindnessPrompt(224, "Teach a friend a basic self-defense move.", "teaching"),
        KindnessPrompt(225, "Help set up technology for an elderly neighbor.", "community"),
        KindnessPrompt(226, "Compliment someone’s positive attitude.", "appreciation"),
        KindnessPrompt(227, "Share a homemade dessert with a friend.", "generosity"),
        KindnessPrompt(228, "Offer to help clean up after a sports game.", "community"),
        KindnessPrompt(229, "Donate canned goods to a local food bank.", "charity"),
        KindnessPrompt(230, "Send an encouraging email to your team.", "workplace"),
        KindnessPrompt(231, "Help paint a community mural.", "environment"),
        KindnessPrompt(232, "Compliment a stranger’s kindness.", "social"),
        KindnessPrompt(233, "Share a historical fact with someone curious.", "teaching"),
        KindnessPrompt(234, "Buy an extra snack for a homeless person.", "generosity"),
        KindnessPrompt(235, "Offer to babysit so parents can have a night out.", "community"),
        KindnessPrompt(236, "Help someone fix a leaky faucet.", "courtesy"),
        KindnessPrompt(237, "Leave fresh fruit in a communal area.", "generosity"),
        KindnessPrompt(238, "Organize a litter clean-up walk.", "environment"),
        KindnessPrompt(239, "Send a motivational card to a student.", "gratitude"),
        KindnessPrompt(240, "Share your raincoat with someone caught in the rain.", "courtesy"),
        KindnessPrompt(241, "Offer free tutoring to children in need.", "teaching"),
        KindnessPrompt(242, "Compliment someone’s creative project.", "appreciation"),
        KindnessPrompt(243, "Give extra produce from your garden to neighbors.", "generosity"),
        KindnessPrompt(244, "Organize a bike repair workshop.", "community"),
        KindnessPrompt(245, "Share mental health resources with a friend.", "empathy"),
        KindnessPrompt(246, "Donate unused hygiene products to a shelter.", "charity"),
        KindnessPrompt(247, "Compliment someone’s neat handwriting.", "social"),
        KindnessPrompt(248, "Offer to walk with someone for safety at night.", "courtesy"),
        KindnessPrompt(249, "Teach someone how to bake bread.", "teaching"),
        KindnessPrompt(250, "Plant wildflowers in a public garden.", "environment"),
        KindnessPrompt(251, "Send a thank-you to a crossing guard.", "gratitude"),
        KindnessPrompt(252, "Help a neighbor set up outdoor holiday lights.", "community"),
        KindnessPrompt(253, "Gift reusable straws to friends.", "environment"),
        KindnessPrompt(254, "Compliment a stranger’s thoughtful gesture.", "social"),
        KindnessPrompt(255, "Help someone carry luggage up stairs.", "courtesy"),
        KindnessPrompt(256, "Start a book exchange in your neighborhood.", "community"),
        KindnessPrompt(257, "Buy lunch for a busy service worker.", "generosity"),
        KindnessPrompt(258, "Offer to train a new employee.", "workplace"),
        KindnessPrompt(259, "Donate pet food to an animal rescue center.", "charity"),
        KindnessPrompt(260, "Compliment a child’s polite behavior.", "appreciation"),
        KindnessPrompt(261, "Teach a neighbor how to compost.", "teaching"),
        KindnessPrompt(262, "Pick up trash along a hiking trail.", "environment"),
        KindnessPrompt(263, "Send a thank-you note to a bus driver.", "gratitude"),
        KindnessPrompt(264, "Help a friend prepare for a presentation.", "workplace"),
        KindnessPrompt(265, "Compliment someone’s cooking presentation.", "social"),
        KindnessPrompt(266, "Organize a carpool to save fuel.", "environment"),
        KindnessPrompt(267, "Offer to help clean a community playground.", "community"),
        KindnessPrompt(268, "Share a mindfulness exercise with someone stressed.", "empathy"),
        KindnessPrompt(269, "Donate books to a prison library.", "charity"),
        KindnessPrompt(270, "Give someone a handmade bookmark.", "generosity"),
        KindnessPrompt(271, "Teach someone basic coding skills.", "teaching"),
        KindnessPrompt(272, "Help install a birdhouse in a public space.", "environment"),
        KindnessPrompt(273, "Compliment a friend’s photography skills.", "appreciation"),
        KindnessPrompt(274, "Offer to fetch water for someone working hard.", "courtesy"),
        KindnessPrompt(275, "Share local volunteering opportunities online.", "digital"),
        KindnessPrompt(276, "Organize a donation drive for school supplies.", "charity"),
        KindnessPrompt(277, "Help plant trees in a local park.", "environment"),
        KindnessPrompt(278, "Compliment someone’s clear communication.", "appreciation"),
        KindnessPrompt(279, "Share a free course link with a learner.", "digital"),
        KindnessPrompt(280, "Offer to drive someone to the grocery store.", "courtesy"),
        KindnessPrompt(281, "Gift a handwritten poem to someone special.", "generosity"),
        KindnessPrompt(282, "Help carry shopping bags for a parent with kids.", "courtesy"),
        KindnessPrompt(283, "Invite someone to join your workout session.", "friendship"),
        KindnessPrompt(284, "Donate board games to a family shelter.", "charity"),
        KindnessPrompt(285, "Teach a child how to ride a bicycle.", "teaching"),
        KindnessPrompt(286, "Compliment someone’s time management skills.", "appreciation"),
        KindnessPrompt(287, "Help collect recyclables after an event.", "environment"),
        KindnessPrompt(288, "Share your charger with someone in need.", "courtesy"),
        KindnessPrompt(289, "Organize a small music performance for charity.", "charity"),
        KindnessPrompt(290, "Compliment a stranger’s cheerful mood.", "social"),
        KindnessPrompt(291, "Help a friend rearrange their furniture.", "community"),
        KindnessPrompt(292, "Teach someone how to knit or crochet.", "teaching"),
        KindnessPrompt(293, "Gift someone a reusable shopping bag.", "environment"),
        KindnessPrompt(294, "Compliment a neighbor’s garden.", "appreciation"),
        KindnessPrompt(295, "Offer to watch someone’s kids during errands.", "community"),
        KindnessPrompt(296, "Give a small tip jar donation to a busker.", "generosity"),
        KindnessPrompt(297, "Share inspiring quotes on social media.", "digital"),
        KindnessPrompt(298, "Help wash a neighbor’s car.", "courtesy"),
        KindnessPrompt(299, "Donate art to a hospital or public place.", "charity"),
        KindnessPrompt(300, "Compliment a stranger’s friendly wave.", "social"),
        KindnessPrompt(301, "Teach a friend how to grow herbs indoors.", "teaching"),
        KindnessPrompt(302, "Offer to hold a baby while a parent shops.", "courtesy"),
        KindnessPrompt(303, "Give a public thank-you to a colleague.", "workplace"),
        KindnessPrompt(304, "Organize a zero-waste picnic.", "environment"),
        KindnessPrompt(305, "Compliment a stranger’s helpful advice.", "appreciation"),
        KindnessPrompt(306, "Help deliver meals for a food program.", "charity"),
        KindnessPrompt(307, "Offer to share your notes with a classmate.", "teaching"),
        KindnessPrompt(308, "Gift someone a jar of homemade jam.", "generosity"),
        KindnessPrompt(309, "Help clean graffiti from a wall.", "environment"),
        KindnessPrompt(310, "Compliment a friend’s problem-solving skills.", "appreciation"),
        KindnessPrompt(311, "Offer to pick up trash during a morning walk.", "environment"),
        KindnessPrompt(312, "Teach someone how to recycle properly.", "teaching"),
        KindnessPrompt(313, "Help fix a community notice board.", "community"),
        KindnessPrompt(314, "Donate gently used sports shoes.", "charity"),
        KindnessPrompt(315, "Compliment someone’s generous nature.", "social"),
        KindnessPrompt(316, "Offer to water plants in a public space.", "environment"),
        KindnessPrompt(317, "Give free fruit samples at a farmers’ market.", "generosity"),
        KindnessPrompt(318, "Help move chairs after an event.", "community"),
        KindnessPrompt(319, "Send encouraging quotes to a friend daily for a week.", "friendship"),
        KindnessPrompt(320, "Compliment someone’s patience in a tough situation.", "appreciation"),
        KindnessPrompt(321, "Organize a 'repair café' to fix broken items for free.", "community"),
        KindnessPrompt(322, "Teach basic first aid to neighbors.", "teaching"),
        KindnessPrompt(323, "Leave a thank-you note for your favorite podcast host.", "gratitude"),
        KindnessPrompt(324, "Sponsor a local school field trip.", "charity"),
        KindnessPrompt(325, "Volunteer to read stories at a community children's hour.", "community"),
        KindnessPrompt(326, "Set up a rotating free pantry for nonperishable food in your area.", "charity"),
        KindnessPrompt(327, "Run a free workshop on internet safety for seniors.", "teaching"),
        KindnessPrompt(328, "Write a LinkedIn recommendation for a deserving colleague.", "workplace"),
        KindnessPrompt(329, "Bring cold water and snacks to outdoor workers on a hot day.", "generosity"),
        KindnessPrompt(330, "Mentor a startup founder or small business owner pro bono.", "workplace"),
        KindnessPrompt(331, "Create a neighborhood kindness scavenger hunt with small surprises.", "community"),
        KindnessPrompt(332, "Host a free online class teaching a hobby you love.", "teaching"),
        KindnessPrompt(333, "Organize a 'thank your local heroes' day with treats for first responders.", "gratitude"),
        KindnessPrompt(334, "Compile and share a list of local mental health resources.", "empathy"),
        KindnessPrompt(335, "Run a community skills-swap event where neighbors trade talents.", "community"),
        KindnessPrompt(336, "Collect and donate old eyeglasses to a vision charity.", "charity"),
        KindnessPrompt(337, "Organize a free basic car maintenance clinic for drivers in need.", "community"),
        KindnessPrompt(338, "Help a neighbor create a simple home emergency kit.", "courtesy"),
        KindnessPrompt(339, "Record and send a video message of appreciation to a teacher.", "gratitude"),
        KindnessPrompt(340, "Collect used batteries and recycle them at a proper facility.", "environment"),
        KindnessPrompt(341, "Walk someone through using public transport routes and schedules.", "courtesy"),
        KindnessPrompt(342, "Start a 'clean desk' day at work and help colleagues declutter.", "workplace"),
        KindnessPrompt(343, "Host a clothing mending session to teach simple repairs.", "teaching"),
        KindnessPrompt(344, "Make thank-you cards with kids for local healthcare staff.", "gratitude"),
        KindnessPrompt(345, "Set up a community bulletin board for neighbors to offer help.", "community"),
        KindnessPrompt(346, "Bring snacks or small treats to staff at a local animal shelter.", "generosity"),
        KindnessPrompt(347, "Start a neighborhood walking group for companionship and safety.", "friendship"),
        KindnessPrompt(348, "Share practical tips on reducing household waste with friends online.", "environment"),
        KindnessPrompt(349, "Launch a 'thank a teacher' postcard campaign at your school.", "gratitude"),
        KindnessPrompt(350, "Assemble care kits for international students starting at your campus.", "community"),
        KindnessPrompt(351, "Offer one-on-one help to someone wanting to learn budgeting basics.", "teaching"),
        KindnessPrompt(352, "Run a free resume and cover letter review session at a community center.", "workplace"),
        KindnessPrompt(353, "Plant native, pollinator-friendly flowers in a shared green space.", "environment"),
        KindnessPrompt(354, "Create a directory of local support services and distribute it to neighbors.", "community"),
        KindnessPrompt(355, "Donate a subscription or access to online learning for someone in need.", "charity"),
        KindnessPrompt(356, "Start a neighborhood tool library where people can borrow common tools.", "community"),
        KindnessPrompt(357, "Sew and donate pillowcases or blankets to a local clinic.", "charity"),
        KindnessPrompt(358, "Offer to proofread flyers and materials for local nonprofits.", "community"),
        KindnessPrompt(359, "Teach children how to start seeds in biodegradable pots.", "teaching"),
        KindnessPrompt(360, "Collect and donate craft supplies to after-school programs.", "charity"),
        KindnessPrompt(361, "Help install solar-powered lights for a community garden.", "environment"),
        KindnessPrompt(362, "Host a 'no phones' family dinner to focus on conversation.", "friendship"),
        KindnessPrompt(363, "Leave a positive review for a favorite tutor or coach.", "appreciation"),
        KindnessPrompt(364, "Map accessible facilities around your neighborhood and share it online.", "community"),
        KindnessPrompt(365, "Donate gardening tools and gloves to a community garden.", "charity"),
        KindnessPrompt(366, "Volunteer to tutor refugees learning the local language.", "teaching"),
        KindnessPrompt(367, "Bake bread and deliver loaves to a local shelter.", "charity"),
        KindnessPrompt(368, "Start a weekly 'thankful' post highlighting local good deeds.", "digital"),
        KindnessPrompt(369, "Help a friend organize and back up their digital photos.", "friendship"),
        KindnessPrompt(370, "Offer free car-seat safety checks and proper installations for parents.", "community"),
        KindnessPrompt(371, "Sponsor a bench in your neighborhood with a hopeful message.", "community"),
        KindnessPrompt(372, "Contribute to a local emergency relief fund after a disaster.", "charity"),
        KindnessPrompt(373, "Practice interview questions with a job-seeker and give feedback.", "workplace"),
        KindnessPrompt(374, "Pick up and deliver medication for an immobile neighbor.", "community"),
        KindnessPrompt(375, "Create a lending shelf of kids' toys for neighbors to borrow.", "community"),
        KindnessPrompt(376, "Host a 'tech tune-up' day where people bring devices for help.", "teaching"),
        KindnessPrompt(377, "Translate key documents for newcomers to your area.", "community"),
        KindnessPrompt(378, "Volunteer regularly to help maintain a local community garden.", "community"),
        KindnessPrompt(379, "Make and post a public thank-you sign for neighborhood volunteers.", "gratitude"),
        KindnessPrompt(380, "Run a solar-lamp building workshop to provide light for low-income homes.", "environment"),
        KindnessPrompt(381, "Offer virtual career-mentoring sessions to students or early-career people.", "workplace"),
        KindnessPrompt(382, "Help set up and maintain a community volunteer registry.", "community"),
        KindnessPrompt(383, "Recognize a helpful neighbor publicly with a 'Neighbor of the Month' note.", "appreciation"),
        KindnessPrompt(384, "Donate spare laptop chargers and cables to a school or clinic.", "charity"),
        KindnessPrompt(385, "Organize inbox-cleanup help for a friend overwhelmed by email.", "friendship"),
        KindnessPrompt(386, "Teach teens basic meal-prep and grocery budgeting skills.", "teaching"),
        KindnessPrompt(387, "Repair and clean donated coats before distributing them to those in need.", "charity"),
        KindnessPrompt(388, "Create a shared playlist where neighbors add uplifting songs.", "digital"),
        KindnessPrompt(389, "Sponsor art classes for children at a community center.", "charity"),
        KindnessPrompt(390, "Offer complimentary headshots for people applying for jobs.", "workplace"),
        KindnessPrompt(391, "Start a 'walking school bus' so groups of kids walk safely to school.", "community"),
        KindnessPrompt(392, "Donate specialized tools or equipment to vocational apprentices.", "charity"),
        KindnessPrompt(393, "Offer a free car wash day for frontline workers.", "generosity"),
        KindnessPrompt(394, "Volunteer as a friendly companion to accompany seniors to appointments.", "empathy"),
        KindnessPrompt(395, "Create a 'gratitude jar' at work for colleagues to add thank-you notes.", "workplace"),
        KindnessPrompt(396, "Help someone apply for government benefits or social services online.", "community"),
        KindnessPrompt(397, "Donate unused airline miles or train passes to someone visiting family.", "generosity"),
        KindnessPrompt(398, "Help a student build an online portfolio showcasing their work.", "teaching"),
        KindnessPrompt(399, "Teach colleagues CPR and AED use at a workplace session.", "teaching"),
        KindnessPrompt(400, "Donate an annual subscription to an educational platform for a child.", "charity"),
        KindnessPrompt(401, "Volunteer as a reading buddy for children in hospital wards.", "charity"),
        KindnessPrompt(402, "Share a packet of free educational printables with local parents.", "teaching"),
        KindnessPrompt(403, "Organize a kitchenware drive and donate to families setting up new homes.", "charity"),
        KindnessPrompt(404, "Host a free photography workshop for local teens.", "teaching"),
        KindnessPrompt(405, "Help design and distribute a flyer for a neighborhood fundraiser.", "community"),
        KindnessPrompt(406, "Deliver groceries anonymously to a family doing it tough.", "generosity"),
        KindnessPrompt(407, "Run a helmet-safety distribution and fitting day for kids.", "community"),
        KindnessPrompt(408, "Offer pro bono tax-filing assistance for low-income households.", "charity"),
        KindnessPrompt(409, "Pair up new neighbors with existing ones as welcome buddies.", "community"),
        KindnessPrompt(410, "Back up a friend's photos and teach them simple archiving techniques.", "friendship"),
        KindnessPrompt(411, "Coordinate a card-making session to thank hospital nurses and staff.", "gratitude"),
        KindnessPrompt(412, "Teach apartment dwellers how to grow food in small containers.", "teaching"),
        KindnessPrompt(413, "Donate gently used baby clothes to a local family support group.", "charity"),
        KindnessPrompt(414, "Help a neighbor install motion-sensor lighting for safety.", "courtesy"),
        KindnessPrompt(415, "Host virtual game nights specifically for isolated seniors.", "friendship"),
        KindnessPrompt(416, "Create a guide to family-friendly nature trails and share it locally.", "environment"),
        KindnessPrompt(417, "Organize a 'fix-it' morning at a senior center for small household repairs.", "community"),
        KindnessPrompt(418, "Donate old textbooks to students who need them for studies.", "charity"),
        KindnessPrompt(419, "Lead a workshop on emotional first aid and active listening.", "teaching"),
        KindnessPrompt(420, "Plant and maintain a small herb garden at a community center and teach others to harvest and use it.", "environment"),
        KindnessPrompt(421, "Leave a thank-you note for the person who cleans your building.", "gratitude"),
        KindnessPrompt(422, "Teach someone how to mend a small tear in clothing.", "teaching"),
        KindnessPrompt(423, "Compile a list of free local services and share it with newcomers.", "community"),
        KindnessPrompt(424, "Sponsor a child’s entry fee for a sports day or competition.", "charity"),
        KindnessPrompt(425, "Plant pollinator-friendly shrubs in a shared area.", "environment"),
        KindnessPrompt(426, "Bring cold drinks to outdoor workers on a hot day.", "generosity"),
        KindnessPrompt(427, "Help set up a community bulletin board for neighbor announcements.", "community"),
        KindnessPrompt(428, "Share homemade jams or preserves with nearby neighbors.", "generosity"),
        KindnessPrompt(429, "Organize a swap of gently used baby or kid items among parents.", "community"),
        KindnessPrompt(430, "Record a short instructional video to help a neighbor use new technology.", "digital"),
        KindnessPrompt(431, "Be an attendee for a friend practicing a presentation and give feedback.", "friendship"),
        KindnessPrompt(432, "Donate a monthly subscription to a news or education service for someone who can’t afford it.", "charity"),
        KindnessPrompt(433, "Host a free beginner’s yoga session in the park.", "community"),
        KindnessPrompt(434, "Offer to accompany someone to a medical appointment for support.", "empathy"),
        KindnessPrompt(435, "Organize a small swap meet for household items to reduce waste.", "environment"),
        KindnessPrompt(436, "Leave a selection of board games in a common area for neighbors to borrow.", "community"),
        KindnessPrompt(437, "Help a friend catalogue and digitize old family photos.", "friendship"),
        KindnessPrompt(438, "Offer a free micro-consultation to a local small business owner.", "workplace"),
        KindnessPrompt(439, "Donate craft kits to an after-school art program.", "charity"),
        KindnessPrompt(440, "Plant a butterfly garden patch and add a sign explaining local species.", "environment"),
        KindnessPrompt(441, "Make and distribute simple emergency contact cards for elderly neighbors.", "courtesy"),
        KindnessPrompt(442, "Share your best tips for stress relief with a busy friend.", "empathy"),
        KindnessPrompt(443, "Volunteer to read short stories to children at a local clinic.", "charity"),
        KindnessPrompt(444, "Set up a free lending rack for DVDs, books, or tools at a community space.", "community"),
        KindnessPrompt(445, "Offer to proofread and format a local nonprofit’s brochure.", "community"),
        KindnessPrompt(446, "Give a bus pass or taxi voucher to someone who needs a ride.", "generosity"),
        KindnessPrompt(447, "Host a neighborhood map-making session to mark accessible routes.", "community"),
        KindnessPrompt(448, "Make a step-by-step guide for composting and share it locally.", "environment"),
        KindnessPrompt(449, "Donate used musical scores or sheet music to a school music program.", "charity"),
        KindnessPrompt(450, "Offer a tech-free evening where you invite a friend over to talk.", "friendship"),
        KindnessPrompt(451, "Volunteer to help newcomers fill out official forms or registrations.", "community"),
        KindnessPrompt(452, "Create a small wildlife water station in a public green space.", "environment"),
        KindnessPrompt(453, "Send a handwritten letter to an old teacher telling them their impact.", "gratitude"),
        KindnessPrompt(454, "Organize a free language-exchange meetup for locals.", "teaching"),
        KindnessPrompt(455, "Donate prepaid phone cards to people who need them.", "charity"),
        KindnessPrompt(456, "Help a neighbor set up basic home security tips (locks, lighting).", "courtesy"),
        KindnessPrompt(457, "Offer to run a short resume photo shoot for job-seekers.", "workplace"),
        KindnessPrompt(458, "Leave a care package at a community notice board labeled ‘Take if needed’.", "generosity"),
        KindnessPrompt(459, "Host a mini workshop teaching basic bicycle maintenance.", "teaching"),
        KindnessPrompt(460, "Organize a group to paint and brighten a crosswalk or bus stop bench.", "environment"),
        KindnessPrompt(461, "Gift a small journal to someone who likes writing.", "generosity"),
        KindnessPrompt(462, "Help someone set up a simple budget spreadsheet and show how to use it.", "teaching"),
        KindnessPrompt(463, "Collect and donate spices or staples to families setting up new homes.", "charity"),
        KindnessPrompt(464, "Offer to drive an elderly neighbor to grocery shopping once a month.", "community"),
        KindnessPrompt(465, "Create a laminated map of nearby accessible ramps and share it online.", "community"),
        KindnessPrompt(466, "Host an evening of pen-pal writing with isolated seniors.", "friendship"),
        KindnessPrompt(467, "Share tips for reducing single-use plastic with your social circle.", "environment"),
        KindnessPrompt(468, "Provide free babysitting vouchers to exhausted parents in your circle.", "generosity"),
        KindnessPrompt(469, "Run a short class teaching basic email and online safety.", "teaching"),
        KindnessPrompt(470, "Volunteer to help a local garden maintain irrigation systems.", "environment"),
        KindnessPrompt(471, "Donate refurbished phones to people searching for work.", "charity"),
        KindnessPrompt(472, "Offer to pick up and deliver library books for neighbors.", "courtesy"),
        KindnessPrompt(473, "Host a potluck where everyone brings food to share with newcomers.", "community"),
        KindnessPrompt(474, "Create an informational flyer about local recycling rules and distribute it.", "environment"),
        KindnessPrompt(475, "Provide a free voice recording of a children’s story for a family.", "digital"),
        KindnessPrompt(476, "Make a list of accessible, free activities for families and share it.", "friendship"),
        KindnessPrompt(477, "Offer to teach someone how to prepare quick, healthy meals.", "teaching"),
        KindnessPrompt(478, "Plant a row of native shrubs to prevent soil erosion in a public area.", "environment"),
        KindnessPrompt(479, "Donate emergency toiletries kits to a women’s shelter.", "charity"),
        KindnessPrompt(480, "Host a ‘welcome to the neighborhood’ tea for new residents.", "community"),
        KindnessPrompt(481, "Offer to help a coworker set up a LinkedIn profile.", "workplace"),
        KindnessPrompt(482, "Create and leave a selection of pocket-sized uplifting quotes in public places.", "gratitude"),
        KindnessPrompt(483, "Help a local teacher assemble classroom resources.", "community"),
        KindnessPrompt(484, "Offer to drive a carpool for students to an extracurricular event.", "courtesy"),
        KindnessPrompt(485, "Teach someone how to prepare a simple emergency meal from pantry items.", "teaching"),
        KindnessPrompt(486, "Volunteer to monitor a community cleanup day’s logistics.", "community"),
        KindnessPrompt(487, "Contribute funds to restore a local playground swing or slide.", "charity"),
        KindnessPrompt(488, "Host a swap of houseplants to help people green their homes.", "environment"),
        KindnessPrompt(489, "Offer to help a friend write a thank-you speech for an award.", "friendship"),
        KindnessPrompt(490, "Create a short guide on how to access free mental health hotlines and share it.", "empathy"),
        KindnessPrompt(491, "Donate protective work gloves and tools to a neighborhood garden crew.", "charity"),
        KindnessPrompt(492, "Offer to help neighbors move heavy boxes for a few hours.", "community"),
        KindnessPrompt(493, "Teach children how to identify common local birds during a walk.", "teaching"),
        KindnessPrompt(494, "Organize a clothing swap focused on professional attire for interviews.", "charity"),
        KindnessPrompt(495, "Plant fruit-bearing shrubs where community members can harvest responsibly.", "environment"),
        KindnessPrompt(496, "Leave a small bag of seeds with instructions in a community library.", "community"),
        KindnessPrompt(497, "Help a friend set up automatic bill payments to reduce stress.", "friendship"),
        KindnessPrompt(498, "Donate a month of groceries to a family in need anonymously.", "charity"),
        KindnessPrompt(499, "Lead a short session on how to spot online job scams.", "teaching"),
        KindnessPrompt(500, "Offer to collect and redistribute leftover food from an event to a shelter.", "generosity"),
        KindnessPrompt(501, "Set up a neighborhood watch group to support vulnerable residents.", "community"),
        KindnessPrompt(502, "Create and pin a list of quiet hours and respectful guidelines for shared spaces.", "courtesy"),
        KindnessPrompt(503, "Host a cake or cookie swap to share baking with those who can’t cook.", "generosity"),
        KindnessPrompt(504, "Volunteer to read aloud during local council or PTA meetings for accessibility.", "community"),
        KindnessPrompt(505, "Provide a basic tutorial on how to access local government services online.", "teaching"),
        KindnessPrompt(506, "Give a welcoming gift basket to a family moving into public housing.", "charity"),
        KindnessPrompt(507, "Organize a free skill-share session where people teach one another.", "community"),
        KindnessPrompt(508, "Plant native groundcover to reduce maintenance on public verges.", "environment"),
        KindnessPrompt(509, "Help someone set up voicemail and phone organization tips.", "courtesy"),
        KindnessPrompt(510, "Donate sporting event tickets to groups who can’t usually attend.", "generosity"),
        KindnessPrompt(511, "Volunteer to help decorate a community center for a holiday.", "community"),
        KindnessPrompt(512, "Create an easy checklist for prepping a home for storms and share it.", "environment"),
        KindnessPrompt(513, "Offer beginner lessons in a musical instrument you play.", "teaching"),
        KindnessPrompt(514, "Collect expired-but-still-usable school supplies and donate them.", "charity"),
        KindnessPrompt(515, "Host a quiet reading hour in a local park for families.", "friendship"),
        KindnessPrompt(516, "Help an elder set up video-calling with distant relatives.", "courtesy"),
        KindnessPrompt(517, "Donate money or time to plant street trees along a local road.", "environment"),
        KindnessPrompt(518, "Assemble and hand out small kindness cards with uplifting actions to try for the day.", "gratitude"),
        KindnessPrompt(519, "Volunteer to provide transportation to a community vaccination drive.", "community"),
        KindnessPrompt(520, "Offer a free short course on public-speaking basics for youth.", "teaching"),
        KindnessPrompt(521, "Donate pet food to your local animal shelter.", "charity"),
        KindnessPrompt(522, "Offer to help an elderly neighbor carry groceries.", "helping"),
        KindnessPrompt(523, "Organize a park clean-up with friends.", "environment"),
        KindnessPrompt(524, "Pay for someone’s coffee anonymously.", "generosity"),
        KindnessPrompt(525, "Send a thank-you email to a coworker.", "gratitude"),
        KindnessPrompt(526, "Leave a book you loved in a public place for someone else to find.", "sharing"),
        KindnessPrompt(527, "Volunteer to tutor a student in need.", "teaching"),
        KindnessPrompt(528, "Plant flowers in a neglected public space.", "beautifying"),
        KindnessPrompt(529, "Bring snacks to a community meeting.", "hospitality"),
        KindnessPrompt(530, "Write a handwritten letter to a distant relative.", "connection"),
        KindnessPrompt(531, "Give a stranger a genuine compliment.", "kindness"),
        KindnessPrompt(532, "Donate gently used clothes to a shelter.", "charity"),
        KindnessPrompt(533, "Help someone carry a heavy package.", "helping"),
        KindnessPrompt(534, "Organize a free workshop on basic budgeting.", "teaching"),
        KindnessPrompt(535, "Offer your seat to someone on public transport.", "respect"),
        KindnessPrompt(536, "Bake cookies for your coworkers.", "sharing"),
        KindnessPrompt(537, "Leave an uplifting sticky note on a bathroom mirror.", "positivity"),
        KindnessPrompt(538, "Help a neighbor water their plants while they’re away.", "helping"),
        KindnessPrompt(539, "Donate books to a school library.", "charity"),
        KindnessPrompt(540, "Share a skill online for free.", "teaching"),
        KindnessPrompt(541, "Organize a community mural painting day to beautify a neglected wall.", "art"),
        KindnessPrompt(542, "Write a thank-you letter to your favorite teacher from school.", "gratitude"),
        KindnessPrompt(543, "Host a free repair café where volunteers fix broken household items.", "community"),
        KindnessPrompt(544, "Offer to walk an elderly neighbor’s dog for a week.", "helping"),
        KindnessPrompt(545, "Create care packages for students during exam week.", "support"),
        KindnessPrompt(546, "Help someone learn how to use video calling to connect with family.", "technology"),
        KindnessPrompt(547, "Start a skill-swap program in your neighborhood.", "community"),
        KindnessPrompt(548, "Offer free babysitting for single parents in your area.", "helping"),
        KindnessPrompt(549, "Organize a ‘swap day’ where people trade clothes or books.", "sustainability"),
        KindnessPrompt(550, "Help plant flowers in public spaces to brighten the environment.", "environment"),
        KindnessPrompt(551, "Teach a free beginner’s yoga class in the park.", "health"),
        KindnessPrompt(552, "Offer to proofread a friend’s resume or cover letter.", "career"),
        KindnessPrompt(553, "Surprise a friend with homemade baked goods.", "friendship"),
        KindnessPrompt(554, "Leave uplifting sticky notes on community bulletin boards.", "positivity"),
        KindnessPrompt(555, "Donate blood or register as an organ donor.", "health"),
        KindnessPrompt(556, "Help someone move into a new home.", "helping"),
        KindnessPrompt(557, "Write positive reviews for local small businesses you love.", "support"),
        KindnessPrompt(558, "Set up a free water station for joggers in your neighborhood.", "health"),
        KindnessPrompt(559, "Offer free tutoring in subjects you’re good at.", "education"),
        KindnessPrompt(560, "Collect and donate warm clothes to a homeless shelter.", "charity"),
        KindnessPrompt(561, "Create a digital guide to free local attractions.", "community"),
        KindnessPrompt(562, "Organize a ‘free lemonade’ stand for passersby.", "fun"),
        KindnessPrompt(563, "Help clean up a local beach, park, or nature trail.", "environment"),
        KindnessPrompt(564, "Record bedtime stories for kids in hospitals.", "compassion"),
        KindnessPrompt(565, "Organize a pen-pal program between generations.", "connection"),
        KindnessPrompt(566, "Plant a tree in memory of someone special.", "environment"),
        KindnessPrompt(567, "Bake and deliver bread to your neighbors.", "community"),
        KindnessPrompt(568, "Offer to take photos for a family who can’t afford a photographer.", "helping"),
        KindnessPrompt(569, "Create a neighborhood lending library.", "education"),
        KindnessPrompt(570, "Teach basic budgeting skills to young adults.", "finance"),
        KindnessPrompt(571, "Organize a ‘thank you’ day for local bus drivers.", "gratitude"),
        KindnessPrompt(572, "Offer to run errands for someone recovering from illness.", "helping"),
        KindnessPrompt(573, "Organize a community dance class for fun and exercise.", "health"),
        KindnessPrompt(574, "Share surplus garden produce with neighbors.", "sharing"),
        KindnessPrompt(575, "Send postcards to people in nursing homes.", "connection"),
        KindnessPrompt(576, "Collect unused makeup and hygiene products for shelters.", "charity"),
        KindnessPrompt(577, "Volunteer to help at a local blood drive.", "health"),
        KindnessPrompt(578, "Organize a free language exchange meetup.", "education"),
        KindnessPrompt(579, "Offer free haircuts for those in need.", "helping"),
        KindnessPrompt(580, "Host a game night at a senior center.", "community"),
        KindnessPrompt(581, "Donate unused craft supplies to schools.", "education"),
        KindnessPrompt(582, "Help a local animal shelter with daily chores.", "animals"),
        KindnessPrompt(583, "Organize a neighborhood garage sale for charity.", "charity"),
        KindnessPrompt(584, "Offer to shovel snow for neighbors in winter.", "helping"),
        KindnessPrompt(585, "Organize a cultural food-sharing event.", "diversity"),
        KindnessPrompt(586, "Help set up an online store for a local artisan.", "technology"),
        KindnessPrompt(587, "Sponsor a child’s school supplies for the year.", "education"),
        KindnessPrompt(588, "Offer free portraits for job-seekers’ profiles.", "career"),
        KindnessPrompt(589, "Organize a storytelling night in your neighborhood.", "community"),
        KindnessPrompt(590, "Offer to teach a friend how to swim.", "health"),
        KindnessPrompt(591, "Create a free art workshop for kids.", "art"),
        KindnessPrompt(592, "Help a neighbor repair a fence or gate.", "helping"),
        KindnessPrompt(593, "Organize a group to visit children’s hospitals.", "compassion"),
        KindnessPrompt(594, "Donate board games to a youth center.", "charity"),
        KindnessPrompt(595, "Offer free proofreading for nonprofit organizations.", "helping"),
        KindnessPrompt(596, "Organize a recycling drive in your area.", "environment"),
        KindnessPrompt(597, "Host a free photography walk for beginners.", "art"),
        KindnessPrompt(598, "Help set up technology for seniors.", "technology"),
        KindnessPrompt(599, "Create a free online guide for job interview tips.", "career"),
        KindnessPrompt(600, "Organize a day to clean up local hiking trails.", "environment"),
        KindnessPrompt(601, "Help someone practice a presentation or speech.", "teaching"),
        KindnessPrompt(602, "Host a zero-waste lifestyle workshop.", "sustainability"),
        KindnessPrompt(603, "Organize a ‘read aloud’ session at a library.", "education"),
        KindnessPrompt(604, "Offer to drive someone to important appointments.", "helping"),
        KindnessPrompt(605, "Make handmade greeting cards for hospital patients.", "compassion"),
        KindnessPrompt(606, "Organize a school backpack donation drive.", "charity"),
        KindnessPrompt(607, "Help someone learn to ride a bicycle.", "teaching"),
        KindnessPrompt(608, "Offer free plant cuttings to neighbors.", "environment"),
        KindnessPrompt(609, "Organize a free fitness bootcamp in the park.", "health"),
        KindnessPrompt(610, "Volunteer to read to visually impaired people.", "compassion"),
        KindnessPrompt(611, "Help restore old photographs for families.", "art"),
        KindnessPrompt(612, "Host a ‘thank your mentor’ event.", "gratitude"),
        KindnessPrompt(613, "Set up a community notice board for events.", "community"),
        KindnessPrompt(614, "Offer free résumé-building workshops.", "career"),
        KindnessPrompt(615, "Plant pollinator-friendly flowers in your yard.", "environment"),
        KindnessPrompt(616, "Help clean and repaint a local playground.", "community"),
        KindnessPrompt(617, "Donate blankets to an animal shelter.", "animals"),
        KindnessPrompt(618, "Host a storytelling circle for kids.", "education"),
        KindnessPrompt(619, "Create a neighborhood emergency contact list.", "safety"),
        KindnessPrompt(620, "Help organize a charity run or walk.", "charity"),
        KindnessPrompt(621, "Offer free beginner’s guitar lessons.", "music"),
        KindnessPrompt(622, "Volunteer to teach basic computer skills.", "technology"),
        KindnessPrompt(623, "Organize a free art exhibition for local artists.", "art"),
        KindnessPrompt(624, "Host a kindness rock painting day.", "positivity"),
        KindnessPrompt(625, "Help paint a community center.", "community"),
        KindnessPrompt(626, "Collect extra school supplies for underprivileged kids.", "charity"),
        KindnessPrompt(627, "Host a documentary screening on social issues.", "education"),
        KindnessPrompt(628, "Teach a cooking class for healthy low-cost meals.", "health"),
        KindnessPrompt(629, "Offer to pet-sit for free when friends are away.", "animals"),
        KindnessPrompt(630, "Organize a book club for personal growth.", "education"),
        KindnessPrompt(631, "Donate old laptops or tablets to students.", "technology"),
        KindnessPrompt(632, "Help a local nonprofit improve their website.", "technology"),
        KindnessPrompt(633, "Create a neighborhood compost bin.", "environment"),
        KindnessPrompt(634, "Host a ‘meet your neighbors’ picnic.", "community"),
        KindnessPrompt(635, "Offer free proofreading for students’ essays.", "education"),
        KindnessPrompt(636, "Help build raised garden beds for a school.", "environment"),
        KindnessPrompt(637, "Donate art supplies to a hospital children’s wing.", "charity"),
        KindnessPrompt(638, "Organize a no-buy month challenge with friends.", "sustainability"),
        KindnessPrompt(639, "Help paint murals in a children’s ward.", "art"),
        KindnessPrompt(640, "Offer to teach photography to teens.", "art"),
        KindnessPrompt(641, "Set up a tool library for shared use.", "community"),
        KindnessPrompt(642, "Host a free outdoor movie night.", "fun"),
        KindnessPrompt(643, "Organize a clothing repair workshop.", "sustainability"),
        KindnessPrompt(644, "Offer to translate documents for free.", "helping"),
        KindnessPrompt(645, "Host a poetry reading night for local poets.", "art"),
        KindnessPrompt(646, "Help create a local walking tour map.", "community"),
        KindnessPrompt(647, "Organize a bicycle safety workshop.", "safety"),
        KindnessPrompt(648, "Offer free résumé headshot photography.", "career"),
        KindnessPrompt(649, "Plant a community herb garden.", "environment"),
        KindnessPrompt(650, "Help seniors digitize their photo albums.", "technology"),
        KindnessPrompt(651, "Organize a free weekend sports event for neighborhood kids.", "community"),
        KindnessPrompt(652, "Volunteer to coach a local youth team for a season.", "mentoring"),
        KindnessPrompt(653, "Create a lending library of sports gear for kids who can’t afford it.", "donation"),
        KindnessPrompt(654, "Offer a free beginner’s class in yoga or meditation in the park.", "teaching"),
        KindnessPrompt(655, "Host a free photography workshop for teens.", "teaching"),
        KindnessPrompt(656, "Help a new driver practice parallel parking safely.", "mentoring"),
        KindnessPrompt(657, "Offer to edit and review a student’s college application essay.", "mentoring"),
        KindnessPrompt(658, "Share your cooking skills with a young person learning to live alone.", "teaching"),
        KindnessPrompt(659, "Organize a street clean-up day with neighbors.", "community"),
        KindnessPrompt(660, "Donate sports tickets to a family who can’t afford them.", "donation"),
        KindnessPrompt(661, "Offer to babysit for free for a single parent in your area.", "helping"),
        KindnessPrompt(662, "Surprise a neighbor with a homemade dessert.", "kindness"),
        KindnessPrompt(663, "Offer your old but working laptop to a student who needs one.", "donation"),
        KindnessPrompt(664, "Help an elderly person organize their photo albums.", "helping"),
        KindnessPrompt(665, "Read aloud to someone with poor eyesight.", "helping"),
        KindnessPrompt(666, "Set up a free community puzzle and board game night.", "community"),
        KindnessPrompt(667, "Bring reusable shopping bags to give away outside a grocery store.", "environment"),
        KindnessPrompt(668, "Volunteer to walk dogs at a local shelter.", "volunteering"),
        KindnessPrompt(669, "Help plant flowers in public spaces to beautify the neighborhood.", "environment"),
        KindnessPrompt(670, "Give free basic tech lessons to older adults.", "teaching"),
        KindnessPrompt(671, "Start a weekly breakfast meet-up for neighbors.", "community"),
        KindnessPrompt(672, "Offer free tutoring to kids struggling with math.", "teaching"),
        KindnessPrompt(673, "Help someone prepare for a job interview.", "mentoring"),
        KindnessPrompt(674, "Cook a meal for a family going through a tough time.", "kindness"),
        KindnessPrompt(675, "Organize a free music jam session for local musicians.", "community"),
        KindnessPrompt(676, "Offer to translate important documents for someone who needs it.", "helping"),
        KindnessPrompt(677, "Help a local shop set up social media for free.", "helping"),
        KindnessPrompt(678, "Leave an anonymous gift card for groceries in someone’s mailbox.", "kindness"),
        KindnessPrompt(679, "Organize a free skill-swap day where neighbors teach each other.", "community"),
        KindnessPrompt(680, "Offer free babysitting during community events.", "helping"),
        KindnessPrompt(681, "Host a free bike maintenance workshop.", "teaching"),
        KindnessPrompt(682, "Clean the snow off a neighbor’s driveway without being asked.", "helping"),
        KindnessPrompt(683, "Set up a free lemonade stand for passersby on a hot day.", "kindness"),
        KindnessPrompt(684, "Help a friend declutter their home.", "helping"),
        KindnessPrompt(685, "Donate books to a small rural library.", "donation"),
        KindnessPrompt(686, "Offer to help with homework at a community center.", "teaching"),
        KindnessPrompt(687, "Plant a tree in a public park.", "environment"),
        KindnessPrompt(688, "Host a free career advice seminar for high schoolers.", "mentoring"),
        KindnessPrompt(689, "Organize a coat drive before winter.", "donation"),
        KindnessPrompt(690, "Help set up decorations for a community event.", "helping"),
        KindnessPrompt(691, "Offer to carry groceries for someone struggling.", "helping"),
        KindnessPrompt(692, "Create a free online resource for local job openings.", "helping"),
        KindnessPrompt(693, "Offer resume writing help to unemployed individuals.", "mentoring"),
        KindnessPrompt(694, "Help a farmer harvest crops during peak season.", "volunteering"),
        KindnessPrompt(695, "Donate blood to a local blood bank.", "donation"),
        KindnessPrompt(696, "Organize a free dog-training demonstration.", "teaching"),
        KindnessPrompt(697, "Offer your driveway for neighbors’ overflow parking.", "helping"),
        KindnessPrompt(698, "Help someone move heavy furniture.", "helping"),
        KindnessPrompt(699, "Start a weekly gratitude circle in your neighborhood.", "community"),
        KindnessPrompt(700, "Offer free language practice sessions for immigrants.", "teaching"),
        KindnessPrompt(701, "Bring snacks to a community meeting.", "kindness"),
        KindnessPrompt(702, "Create and give away homemade bookmarks at the library.", "kindness"),
        KindnessPrompt(703, "Organize a lost-and-found post for your neighborhood online.", "community"),
        KindnessPrompt(704, "Offer to water plants for a traveling neighbor.", "helping"),
        KindnessPrompt(705, "Host a community BBQ with free food for everyone.", "community"),
        KindnessPrompt(706, "Offer to teach basic self-defense for free.", "teaching"),
        KindnessPrompt(707, "Start a neighborhood watch group to keep everyone safe.", "community"),
        KindnessPrompt(708, "Help repair a neighbor’s fence.", "helping"),
        KindnessPrompt(709, "Host a free storytelling night for kids.", "teaching"),
        KindnessPrompt(710, "Give away fresh produce from your garden.", "donation"),
        KindnessPrompt(711, "Help set up a free Wi-Fi hotspot in your neighborhood.", "helping"),
        KindnessPrompt(712, "Offer to pet-sit for free during someone’s vacation.", "helping"),
        KindnessPrompt(713, "Host a recycling awareness event.", "environment"),
        KindnessPrompt(714, "Donate unused craft supplies to a school.", "donation"),
        KindnessPrompt(715, "Offer free career coaching for recent graduates.", "mentoring"),
        KindnessPrompt(716, "Start a community walking group for fitness.", "community"),
        KindnessPrompt(717, "Give a neighbor extra seeds to start their own garden.", "kindness"),
        KindnessPrompt(718, "Help a student prepare for a science fair.", "mentoring"),
        KindnessPrompt(719, "Offer to read to kids at a local daycare.", "teaching"),
        KindnessPrompt(720, "Create a free booklet on low-cost healthy recipes.", "helping"),
        KindnessPrompt(721, "Host a neighborhood swap meet for clothes and goods.", "community"),
        KindnessPrompt(722, "Offer to proofread important documents for someone.", "helping"),
        KindnessPrompt(723, "Give your extra kitchen utensils to a new neighbor.", "donation"),
        KindnessPrompt(724, "Help an artist set up an exhibition space.", "helping"),
        KindnessPrompt(725, "Offer to record and edit a video for a local cause.", "helping"),
        KindnessPrompt(726, "Create a community notice board in a public space.", "community"),
        KindnessPrompt(727, "Host a free calligraphy or handwriting workshop.", "teaching"),
        KindnessPrompt(728, "Offer to design a flyer for a charity event.", "helping"),
        KindnessPrompt(729, "Give away your unused musical instrument to a student.", "donation"),
        KindnessPrompt(730, "Host a free neighborhood trivia night.", "community"),
        KindnessPrompt(731, "Help a senior learn to use video calls to talk to family.", "teaching"),
        KindnessPrompt(732, "Start a carpool group for neighbors to save fuel.", "community"),
        KindnessPrompt(733, "Donate pet food to an animal shelter.", "donation"),
        KindnessPrompt(734, "Offer to help someone study for their driving test.", "mentoring"),
        KindnessPrompt(735, "Host a free composting workshop.", "environment"),
        KindnessPrompt(736, "Help a local charity with data entry work.", "volunteering"),
        KindnessPrompt(737, "Offer to fix someone’s leaky faucet.", "helping"),
        KindnessPrompt(738, "Organize a mural painting day for public walls.", "community"),
        KindnessPrompt(739, "Give free plant cuttings to neighbors.", "kindness"),
        KindnessPrompt(740, "Offer free tutoring in coding basics.", "teaching"),
        KindnessPrompt(741, "Help organize a fundraising walkathon.", "community"),
        KindnessPrompt(742, "Host a free workshop on bike safety.", "teaching"),
        KindnessPrompt(743, "Start a weekly letter-writing project for isolated seniors.", "kindness"),
        KindnessPrompt(744, "Offer to photograph a special family event for free.", "helping"),
        KindnessPrompt(745, "Create a neighborhood emergency contact list.", "community"),
        KindnessPrompt(746, "Organize a cultural food-sharing night.", "community"),
        KindnessPrompt(747, "Donate gently used coats to a homeless shelter.", "donation"),
        KindnessPrompt(748, "Help set up a school’s computer lab.", "helping"),
        KindnessPrompt(749, "Offer free babysitting during local elections.", "helping"),
        KindnessPrompt(750, "Start a neighborhood newsletter with positive news.", "community"),
        KindnessPrompt(751, "Host a free origami-making session for kids.", "teaching"),
        KindnessPrompt(752, "Help a neighbor set up their new phone.", "helping"),
        KindnessPrompt(753, "Give your old textbooks to a student in need.", "donation"),
        KindnessPrompt(754, "Offer to clean a local monument.", "volunteering"),
        KindnessPrompt(755, "Start a free guitar lesson group in the park.", "teaching"),
        KindnessPrompt(756, "Organize a free fitness boot camp.", "community"),
        KindnessPrompt(757, "Offer your artistic skills to paint a public bench.", "helping"),
        KindnessPrompt(758, "Create a pet adoption awareness campaign.", "community"),
        KindnessPrompt(759, "Offer to help a family create a household budget.", "mentoring"),
        KindnessPrompt(760, "Give away reusable water bottles to promote sustainability.", "environment"),
        KindnessPrompt(761, "Help coordinate a blood donation camp.", "volunteering"),
        KindnessPrompt(762, "Offer free knitting lessons to beginners.", "teaching"),
        KindnessPrompt(763, "Host a free storytelling workshop for teens.", "teaching"),
        KindnessPrompt(764, "Clean up litter after a community event.", "environment"),
        KindnessPrompt(765, "Offer to teach basic car maintenance for free.", "teaching"),
        KindnessPrompt(766, "Donate extra tools to a community workshop.", "donation"),
        KindnessPrompt(767, "Host a neighborhood ice cream social.", "community"),
        KindnessPrompt(768, "Help organize a nature hike for kids.", "community"),
        KindnessPrompt(769, "Offer to fix torn clothes for free.", "helping"),
        KindnessPrompt(770, "Give away baby clothes your children have outgrown.", "donation"),
        KindnessPrompt(771, "Plant herbs in a public space for anyone to use.", "environment"),
        KindnessPrompt(772, "Start a community chess club.", "community"),
        KindnessPrompt(773, "Offer to translate a local museum guide into another language.", "helping"),
        KindnessPrompt(774, "Host a free public art class.", "teaching"),
        KindnessPrompt(775, "Help set up chairs and tables for a public event.", "helping"),
        KindnessPrompt(776, "Donate spare blankets to an animal shelter.", "donation"),
        KindnessPrompt(777, "Organize a community bird-watching day.", "community"),
        KindnessPrompt(778, "Offer to mentor a student interested in your profession.", "mentoring"),
        KindnessPrompt(779, "Create a free public calendar of local events.", "community"),
        KindnessPrompt(780, "Host a neighborhood gardening competition.", "community"),
        KindnessPrompt(781, "Help repair bicycles for free.", "helping"),
        KindnessPrompt(782, "Offer to run errands for someone recovering from surgery.", "helping"),
        KindnessPrompt(783, "Start a community art supply exchange.", "donation"),
        KindnessPrompt(784, "Host a free workshop on creative writing.", "teaching"),
        KindnessPrompt(785, "Offer to check on a neighbor’s house while they’re away.", "helping"),
        KindnessPrompt(786, "Help paint a local school classroom.", "volunteering"),
        KindnessPrompt(787, "Donate gently used toys to a daycare center.", "donation"),
        KindnessPrompt(788, "Create a small book exchange shelf in a public place.", "community"),
        KindnessPrompt(789, "Offer free dance lessons in the park.", "teaching"),
        KindnessPrompt(790, "Help organize a local history walking tour.", "community"),
        KindnessPrompt(791, "Offer your photography skills to a charity for free.", "helping"),
        KindnessPrompt(792, "Start a poetry reading club.", "community"),
        KindnessPrompt(793, "Plant wildflowers to help pollinators.", "environment"),
        KindnessPrompt(794, "Offer to teach basic first aid for free.", "teaching"),
        KindnessPrompt(795, "Give away gently used handbags to those in need.", "donation"),
        KindnessPrompt(796, "Host a free park picnic for families.", "community"),
        KindnessPrompt(797, "Help a neighbor with seasonal yard work.", "helping"),
        KindnessPrompt(798, "Offer to set up an email account for someone unfamiliar with tech.", "helping"),
        KindnessPrompt(799, "Donate stationery to a school in need.", "donation"),
        KindnessPrompt(800, "Organize a free movie night in the park.", "community"),
        KindnessPrompt(801, "Start a friendly neighborhood running group.", "community"),
        KindnessPrompt(802, "Offer to share your professional expertise in a free workshop.", "teaching"),
        KindnessPrompt(803, "Help install shelves or furniture for someone.", "helping"),
        KindnessPrompt(804, "Give away seeds for a community planting project.", "donation"),
        KindnessPrompt(805, "Offer to translate a recipe for a neighbor.", "helping"),
        KindnessPrompt(806, "Host a pet playdate for neighborhood animals.", "community"),
        KindnessPrompt(807, "Clean and restore a neglected park bench.", "environment"),
        KindnessPrompt(808, "Offer to scan and digitize old photos for someone.", "helping"),
        KindnessPrompt(809, "Help organize a local crafts market.", "community"),
        KindnessPrompt(810, "Donate board games to a youth center.", "donation"),
        KindnessPrompt(811, "Teach a free painting class for beginners.", "teaching"),
        KindnessPrompt(812, "Offer to help a local musician record their music.", "helping"),
        KindnessPrompt(813, "Start a free public puzzle table in a park.", "community"),
        KindnessPrompt(814, "Help create signs for a community garden.", "helping"),
        KindnessPrompt(815, "Donate baked goods to a school fundraiser.", "donation"),
        KindnessPrompt(816, "Host a free public singing event.", "community"),
        KindnessPrompt(817, "Teach kids how to play chess for free.", "teaching"),
        KindnessPrompt(818, "Offer to babysit during a community meeting.", "helping"),
        KindnessPrompt(819, "Help maintain a local walking trail.", "environment"),
        KindnessPrompt(820, "Give away blankets to homeless individuals.", "donation"),
        KindnessPrompt(821, "Organize a free public art mural project.", "community"),
        KindnessPrompt(822, "Offer basic home repair lessons for free.", "teaching"),
        KindnessPrompt(823, "Help write grant applications for a nonprofit.", "helping"),
        KindnessPrompt(824, "Donate pet toys to an animal rescue center.", "donation"),
        KindnessPrompt(825, "Host a storytelling night for adults.", "community"),
        KindnessPrompt(826, "Start a book club for young adults.", "community"),
        KindnessPrompt(827, "Help a friend practice for a presentation.", "mentoring"),
        KindnessPrompt(828, "Offer to help someone set up online bill payments.", "helping"),
        KindnessPrompt(829, "Plant shade trees along a public sidewalk.", "environment"),
        KindnessPrompt(830, "Donate extra blankets to a children’s hospital.", "donation"),
        KindnessPrompt(831, "Host a free workshop on digital safety.", "teaching"),
        KindnessPrompt(832, "Help organize a charity sports match.", "community"),
        KindnessPrompt(833, "Offer to organize a neighbor’s garage for them.", "helping"),
        KindnessPrompt(834, "Give away homemade soap to neighbors.", "kindness"),
        KindnessPrompt(835, "Teach basic sign language for free.", "teaching"),
        KindnessPrompt(836, "Help coordinate a food truck day in your neighborhood.", "community"),
        KindnessPrompt(837, "Donate school bags to underprivileged children.", "donation"),
        KindnessPrompt(838, "Offer to translate an educational video for wider access.", "helping"),
        KindnessPrompt(839, "Host a neighborhood camping night.", "community"),
        KindnessPrompt(840, "Help paint a public mural promoting kindness.", "community"),
        KindnessPrompt(841, "Give away free reusable grocery bags.", "environment"),
        KindnessPrompt(842, "Offer to teach basic photography skills.", "teaching"),
        KindnessPrompt(843, "Help a charity with their social media posts.", "helping"),
        KindnessPrompt(844, "Donate musical instruments to a school.", "donation"),
        KindnessPrompt(845, "Host a neighborhood talent show.", "community"),
        KindnessPrompt(846, "Create a map of local walking paths.", "helping"),
        KindnessPrompt(847, "Plant flowers around public signs.", "environment"),
        KindnessPrompt(848, "Offer to fix bicycles for free.", "helping"),
        KindnessPrompt(849, "Donate books to a prison library.", "donation"),
        KindnessPrompt(850, "Host a free open mic night.", "community"),
        KindnessPrompt(851, "Help a neighbor learn to bake bread.", "teaching"),
        KindnessPrompt(852, "Offer to clean someone’s gutters.", "helping"),
        KindnessPrompt(853, "Give away spare chairs or tables to a family in need.", "donation"),
        KindnessPrompt(854, "Help repaint a public fence.", "community"),
        KindnessPrompt(855, "Teach a free class on budgeting basics.", "teaching"),
        KindnessPrompt(856, "Offer to proofread a friend's resume.", "support"),
        KindnessPrompt(857, "Host a documentary screening on an important social issue.", "awareness"),
        KindnessPrompt(858, "Make handmade bookmarks and give them to library visitors.", "crafting"),
        KindnessPrompt(859, "Help someone set up online bill payments.", "technology"),
        KindnessPrompt(860, "Create a safe space for people to talk about mental health.", "support"),
        KindnessPrompt(861, "Bake bread for a neighbor.", "cooking"),
        KindnessPrompt(862, "Help clean up graffiti in your area.", "community"),
        KindnessPrompt(863, "Share a positive story in your community newsletter.", "writing"),
        KindnessPrompt(864, "Teach a friend how to cook a healthy recipe.", "cooking"),
        KindnessPrompt(865, "Volunteer at a disaster relief center.", "volunteering"),
        KindnessPrompt(866, "Make a care package for a family in crisis.", "giving"),
        KindnessPrompt(867, "Organize a local plant swap event.", "community"),
        KindnessPrompt(868, "Help someone study for a test.", "teaching"),
        KindnessPrompt(869, "Donate pet food to a local shelter.", "giving"),
        KindnessPrompt(870, "Translate a document for someone in need.", "language"),
        KindnessPrompt(871, "Sew reusable shopping bags for friends.", "crafting"),
        KindnessPrompt(872, "Help organize a fundraiser for medical expenses.", "fundraising"),
        KindnessPrompt(873, "Gift seeds to neighbors for their gardens.", "giving"),
        KindnessPrompt(874, "Hold a clothing repair workshop.", "teaching"),
        KindnessPrompt(875, "Share free mental health hotlines online.", "awareness"),
        KindnessPrompt(876, "Host a game night for new neighbors.", "community"),
        KindnessPrompt(877, "Offer a ride to someone without transportation.", "support"),
        KindnessPrompt(878, "Create a YouTube tutorial to teach a useful skill.", "teaching"),
        KindnessPrompt(879, "Help someone complete online government forms.", "technology"),
        KindnessPrompt(880, "Volunteer as a mentor for a youth program.", "mentoring"),
        KindnessPrompt(881, "Offer to mow the lawn for a neighbor.", "support"),
        KindnessPrompt(882, "Organize a free yoga class in the park.", "community"),
        KindnessPrompt(883, "Bake cookies for local firefighters.", "cooking"),
        KindnessPrompt(884, "Help clean and organize a community center.", "volunteering"),
        KindnessPrompt(885, "Assist with setting up an event for a charity.", "support"),
        KindnessPrompt(886, "Create encouraging art for a public space.", "art"),
        KindnessPrompt(887, "Help fix a broken fence for a neighbor.", "support"),
        KindnessPrompt(888, "Share a skill at a local library workshop.", "teaching"),
        KindnessPrompt(889, "Donate sports equipment to kids in need.", "giving"),
        KindnessPrompt(890, "Offer to babysit for a busy parent.", "support"),
        KindnessPrompt(891, "Run errands for someone recovering from surgery.", "support"),
        KindnessPrompt(892, "Give free haircuts to those in need.", "service"),
        KindnessPrompt(893, "Help a student with their homework.", "teaching"),
        KindnessPrompt(894, "Create a free community recipe book.", "cooking"),
        KindnessPrompt(895, "Host a recycling drive.", "environment"),
        KindnessPrompt(896, "Make welcome kits for refugees.", "giving"),
        KindnessPrompt(897, "Share local volunteer opportunities online.", "awareness"),
        KindnessPrompt(898, "Help set up a public Wi-Fi hotspot.", "technology"),
        KindnessPrompt(899, "Organize a charity fun run.", "fundraising"),
        KindnessPrompt(900, "Give away unused electronics to those in need.", "giving"),
        KindnessPrompt(901, "Help install safety railings in someone's home.", "support"),
        KindnessPrompt(902, "Start a local walking group for seniors.", "community"),
        KindnessPrompt(903, "Make and deliver fresh juice to hospital staff.", "cooking"),
        KindnessPrompt(904, "Create a free online resource guide for parents.", "support"),
        KindnessPrompt(905, "Host a free art class for children.", "teaching"),
        KindnessPrompt(906, "Plant flowers around a bus stop.", "environment"),
        KindnessPrompt(907, "Help a local small business create a website.", "technology"),
        KindnessPrompt(908, "Organize a 'no spend' community swap meet.", "community"),
        KindnessPrompt(909, "Create an audio recording of a book for someone visually impaired.", "service"),
        KindnessPrompt(910, "Donate blood at a local drive.", "giving"),
        KindnessPrompt(911, "Help carry groceries for an elderly person.", "support"),
        KindnessPrompt(912, "Write and deliver thank-you notes to healthcare workers.", "writing"),
        KindnessPrompt(913, "Fix broken toys and donate them.", "crafting"),
        KindnessPrompt(914, "Host a free language practice meetup.", "teaching"),
        KindnessPrompt(915, "Create and share positive affirmation cards.", "art"),
        KindnessPrompt(916, "Help paint over faded road signs in your area.", "community"),
        KindnessPrompt(917, "Share job opportunities with people looking for work.", "support"),
        KindnessPrompt(918, "Make homemade jam for neighbors.", "cooking"),
        KindnessPrompt(919, "Offer basic computer training to seniors.", "teaching"),
        KindnessPrompt(920, "Organize a school supply donation drive.", "fundraising"),
        KindnessPrompt(921, "Help set up a rainwater harvesting system.", "environment"),
        KindnessPrompt(922, "Bake a cake for someone's birthday.", "cooking"),
        KindnessPrompt(923, "Offer to help someone move house.", "support"),
        KindnessPrompt(924, "Create free downloadable study notes.", "teaching"),
        KindnessPrompt(925, "Organize a park bench painting project.", "community"),
        KindnessPrompt(926, "Share surplus vegetables from your garden.", "giving"),
        KindnessPrompt(927, "Help create a community notice board.", "community"),
        KindnessPrompt(928, "Make and donate blankets to shelters.", "giving"),
        KindnessPrompt(929, "Assist in planting trees in public areas.", "environment"),
        KindnessPrompt(930, "Organize a clothes swap event.", "community"),
        KindnessPrompt(931, "Create a free budgeting spreadsheet template.", "support"),
        KindnessPrompt(932, "Help someone prepare for a job interview.", "mentoring"),
        KindnessPrompt(933, "Teach basic sign language to interested people.", "teaching"),
        KindnessPrompt(934, "Clean playground equipment at a local park.", "community"),
        KindnessPrompt(935, "Offer a free pet-sitting service.", "support"),
        KindnessPrompt(936, "Make homemade candles as gifts.", "crafting"),
        KindnessPrompt(937, "Help digitize old photographs for someone.", "technology"),
        KindnessPrompt(938, "Create a public lending library box.", "community"),
        KindnessPrompt(939, "Organize a volunteer day for community projects.", "volunteering"),
        KindnessPrompt(940, "Bake and deliver muffins to bus drivers.", "cooking"),
        KindnessPrompt(941, "Help write a business plan for a friend.", "support"),
        KindnessPrompt(942, "Create a mental wellness challenge for friends.", "awareness"),
        KindnessPrompt(943, "Repair old bicycles and donate them.", "crafting"),
        KindnessPrompt(944, "Start a pet adoption awareness page online.", "awareness"),
        KindnessPrompt(945, "Help decorate a community center for holidays.", "community"),
        KindnessPrompt(946, "Share free workout videos you made.", "teaching"),
        KindnessPrompt(947, "Help clean up after a local event.", "volunteering"),
        KindnessPrompt(948, "Offer free guitar lessons to beginners.", "teaching"),
        KindnessPrompt(949, "Cook and share a meal with someone living alone.", "cooking"),
        KindnessPrompt(950, "Create and hand out reusable produce bags.", "crafting"),
        KindnessPrompt(951, "Help organize a beach clean-up.", "environment"),
        KindnessPrompt(952, "Donate blankets to a homeless shelter.", "giving"),
        KindnessPrompt(953, "Organize a story reading session for kids.", "teaching"),
        KindnessPrompt(954, "Offer to pet walk for busy neighbors.", "support"),
        KindnessPrompt(955, "Make and share a list of local free events.", "awareness"),
        KindnessPrompt(956, "Help fix someone's leaky faucet.", "support"),
        KindnessPrompt(957, "Write encouraging notes for strangers to find.", "writing"),
        KindnessPrompt(958, "Organize a carpool to reduce emissions.", "environment"),
        KindnessPrompt(959, "Create a mini community garden.", "environment"),
        KindnessPrompt(960, "Offer to run errands for a sick friend.", "support"),
        KindnessPrompt(961, "Help design flyers for a charity event.", "support"),
        KindnessPrompt(962, "Offer to water plants for someone on vacation.", "support"),
        KindnessPrompt(963, "Create a tutorial for kids on basic science experiments.", "teaching"),
        KindnessPrompt(964, "Organize a sports day for kids in the neighborhood.", "community"),
        KindnessPrompt(965, "Make bird feeders and place them in the park.", "environment"),
        KindnessPrompt(966, "Help clean an elderly person's home.", "support"),
        KindnessPrompt(967, "Organize a local history walking tour.", "teaching"),
        KindnessPrompt(968, "Donate gently used books to a school.", "giving"),
        KindnessPrompt(969, "Create art to brighten up a hospital ward.", "art"),
        KindnessPrompt(970, "Help install a community compost bin.", "environment"),
        KindnessPrompt(971, "Offer to do laundry for a busy parent.", "support"),
        KindnessPrompt(972, "Teach kids how to play chess.", "teaching"),
        KindnessPrompt(973, "Organize a film night for your community.", "community"),
        KindnessPrompt(974, "Help sew costumes for a school play.", "crafting"),
        KindnessPrompt(975, "Plant shrubs to prevent soil erosion.", "environment"),
        KindnessPrompt(976, "Offer free tutoring in math.", "teaching"),
        KindnessPrompt(977, "Create care packs for delivery drivers.", "giving"),
        KindnessPrompt(978, "Help organize a blood donation camp.", "fundraising"),
        KindnessPrompt(979, "Assist in setting up a local podcast.", "technology"),
        KindnessPrompt(980, "Cook breakfast for a friend in need.", "cooking"),
        KindnessPrompt(981, "Make and donate face masks.", "crafting"),
        KindnessPrompt(982, "Offer to proofread someone's essay.", "support"),
        KindnessPrompt(983, "Teach a neighbor how to use email.", "technology"),
        KindnessPrompt(984, "Create a calming playlist and share it.", "support"),
        KindnessPrompt(985, "Help repair a playground swing.", "community"),
        KindnessPrompt(986, "Organize a dance class for kids.", "community"),
        KindnessPrompt(987, "Donate unused baby clothes.", "giving"),
        KindnessPrompt(988, "Write a poem for someone special.", "writing"),
        KindnessPrompt(989, "Host a free resume writing workshop.", "teaching"),
        KindnessPrompt(990, "Help distribute food at a community pantry.", "volunteering"),
        KindnessPrompt(991, "Clean windows for an elderly neighbor.", "support"),
        KindnessPrompt(992, "Create a mural with local kids.", "art"),
        KindnessPrompt(993, "Start a compost pile in your backyard.", "environment"),
        KindnessPrompt(994, "Help someone pack for a trip.", "support"),
        KindnessPrompt(995, "Give a houseplant to brighten someone's home.", "giving"),
        KindnessPrompt(996, "Create and distribute local walking maps.", "community"),
        KindnessPrompt(997, "Offer to host a study group.", "teaching"),
        KindnessPrompt(998, "Help set up decorations for a festival.", "community"),
        KindnessPrompt(999, "Bake pies for your local police station.", "cooking"),
        KindnessPrompt(1000, "Donate art supplies to a children's shelter.", "giving")
        )

    suspend fun initializeDatabase() {
        val promptCount = dao.getPromptCount()
        if (promptCount == 0) {
            dao.insertPrompts(defaultPrompts)
        }
    }

    suspend fun getDailyPrompt(date: Date = Date()): KindnessPromptWithCompletion {
        val dateString = dateFormatter.format(date)

        // Check if there's already a completion for today
        val existingCompletion = dao.getCompletionByDate(dateString)

        if (existingCompletion != null) {
            // Return the prompt that was already completed today
            val prompt = dao.getPromptById(existingCompletion.promptId)
            return KindnessPromptWithCompletion(prompt!!, existingCompletion)
        }

        // Check if there's already a daily prompt selection for today
        val existingSelection = dao.getDailyPromptSelection(dateString)

        if (existingSelection != null) {
            // Return the previously selected prompt for today
            val prompt = dao.getPromptById(existingSelection.promptId)
            if (prompt != null) {
                return KindnessPromptWithCompletion(prompt, null)
            }
            // If prompt doesn't exist anymore, fall through to select a new one
        }

        // Get all prompts and select one randomly
        val allPrompts = dao.getAllPrompts()
        if (allPrompts.isEmpty()) {
            initializeDatabase()
            return getDailyPrompt(date)
        }

        // Get prompts that have been skipped today
        val skippedTodayIds = dao.getSkippedPromptIdsByDate(dateString)

        // Filter out skipped prompts
        val availablePrompts = allPrompts.filter { it.id !in skippedTodayIds }

        val selectedPrompts = if (availablePrompts.isNotEmpty()) {
            availablePrompts
        } else {
            // If all prompts have been skipped, use all prompts
            allPrompts
        }

        // Use date as seed for consistent daily prompt
        val seed = date.time
        val randomPrompt = selectedPrompts[Random(seed).nextInt(selectedPrompts.size)]

        // Save the daily prompt selection
        val dailySelection = DailyPromptSelection(
            date = dateString,
            promptId = randomPrompt.id
        )
        dao.insertDailyPromptSelection(dailySelection)

        return KindnessPromptWithCompletion(randomPrompt, null)
    }

    suspend fun markPromptAsCompleted(promptId: Int, date: Date = Date(), notes: String = ""): Long {
        val dateString = dateFormatter.format(date)
        val completion = KindnessCompletion(
            promptId = promptId,
            completedDate = dateString,
            notes = notes
        )
        return dao.insertCompletion(completion)
    }

    suspend fun toggleFavorite(completion: KindnessCompletion) {
        val updatedCompletion = completion.copy(isFavorite = !completion.isFavorite)
        dao.updateCompletion(updatedCompletion)
    }

    // History screen methods
    fun getAllCompletions(): Flow<List<KindnessCompletion>> {
        return dao.getAllCompletions()
    }

    fun getFavoriteCompletions(): Flow<List<KindnessCompletion>> {
        return dao.getFavoriteCompletions()
    }

    suspend fun getAllPrompts(): List<KindnessPrompt> {
        return dao.getAllPrompts()
    }

    suspend fun getPromptById(id: Int): KindnessPrompt? {
        return dao.getPromptById(id)
    }

    suspend fun updateCompletion(completion: KindnessCompletion) {
        dao.updateCompletion(completion)
    }

    suspend fun deleteCompletion(completion: KindnessCompletion) {
        dao.deleteCompletion(completion)
    }

    suspend fun getCurrentStreak(): Int {
        val today = Date()
        val calendar = Calendar.getInstance()
        var streak = 0

        calendar.time = today

        // Check backwards from today to find consecutive days
        while (true) {
            val dateString = dateFormatter.format(calendar.time)
            val completion = dao.getCompletionByDate(dateString)

            if (completion != null) {
                streak++
                calendar.add(Calendar.DAY_OF_MONTH, -1) // Go back one day
            } else {
                break
            }
        }

        return streak
    }

    suspend fun skipPrompt(promptId: Int, date: Date = Date(), reason: String = ""): Long {
        val dateString = dateFormatter.format(date)
        val skippedPrompt = SkippedPrompt(
            promptId = promptId,
            skippedDate = dateString,
            reason = reason
        )
        return dao.insertSkippedPrompt(skippedPrompt)
    }

    suspend fun getNextAvailablePrompt(date: Date = Date()): KindnessPromptWithCompletion {
        val dateString = dateFormatter.format(date)

        // Get all prompts
        val allPrompts = dao.getAllPrompts()
        if (allPrompts.isEmpty()) {
            initializeDatabase()
            return getNextAvailablePrompt(date)
        }

        // Get prompts that have been skipped today
        val skippedTodayIds = dao.getSkippedPromptIdsByDate(dateString)

        // Filter out skipped prompts
        val availablePrompts = allPrompts.filter { it.id !in skippedTodayIds }

        val selectedPrompt = if (availablePrompts.isEmpty()) {
            // If all prompts have been skipped, select from all prompts
            val seed = date.time
            allPrompts[Random(seed).nextInt(allPrompts.size)]
        } else {
            // Use date as seed for consistent selection, but add skip count to vary selection
            val seed = date.time + skippedTodayIds.size
            availablePrompts[Random(seed).nextInt(availablePrompts.size)]
        }

        // Update the daily prompt selection with the new prompt
        val dailySelection = DailyPromptSelection(
            date = dateString,
            promptId = selectedPrompt.id
        )
        dao.insertDailyPromptSelection(dailySelection)

        return KindnessPromptWithCompletion(selectedPrompt, null)
    }

    private fun getRandomPromptFromAll(allPrompts: List<KindnessPrompt>, date: Date): KindnessPromptWithCompletion {
        val seed = date.time
        val randomPrompt = allPrompts[Random(seed).nextInt(allPrompts.size)]
        return KindnessPromptWithCompletion(randomPrompt, null)
    }

    suspend fun cleanupOldSkippedPrompts(daysToKeep: Int = 7) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -daysToKeep)
        val cutoffDateString = dateFormatter.format(calendar.time)
        dao.deleteOldSkippedPrompts(cutoffDateString)

        // Also cleanup old daily prompt selections
        dao.deleteOldDailyPromptSelections(cutoffDateString)
    }

    // UserProgress and Streak Management
    suspend fun getUserProgress(): UserProgress {
        return dao.getUserProgress() ?: UserProgress(
            id = 1,
            currentStreak = 0,
            bestStreak = 0,
            lastCompletedDate = "",
            totalCompleted = 0,
            startDate = dateFormatter.format(Date())
        ).also {
            dao.insertOrUpdateUserProgress(it)
        }
    }

    suspend fun updateStreakOnCompletion(completionDate: Date = Date()): UserProgress {
        val currentProgress = getUserProgress()
        val completionDateString = dateFormatter.format(completionDate)

        // Don't update streak if already completed today
        if (currentProgress.lastCompletedDate == completionDateString) {
            return currentProgress
        }

        val newStreak = when {
            currentProgress.lastCompletedDate.isEmpty() -> 1 // First completion
            isConsecutiveDay(currentProgress.lastCompletedDate, completionDateString) -> currentProgress.currentStreak + 1
            else -> 1 // Streak broken, start over
        }

        val newBestStreak = maxOf(newStreak, currentProgress.bestStreak)
        val newTotalCompleted = currentProgress.totalCompleted + 1

        val updatedProgress = currentProgress.copy(
            currentStreak = newStreak,
            bestStreak = newBestStreak,
            lastCompletedDate = completionDateString,
            totalCompleted = newTotalCompleted
        )

        dao.insertOrUpdateUserProgress(updatedProgress)
        return updatedProgress
    }

    private fun isConsecutiveDay(lastCompletedDate: String, newCompletionDate: String): Boolean {
        if (lastCompletedDate.isEmpty()) return false

        try {
            val lastDate = dateFormatter.parse(lastCompletedDate) ?: return false
            val newDate = dateFormatter.parse(newCompletionDate) ?: return false

            val calendar = Calendar.getInstance()
            calendar.time = lastDate
            calendar.add(Calendar.DAY_OF_MONTH, 1) // Add one day to last date

            // Check if the new completion is exactly one day after the last completion
            val expectedDate = dateFormatter.format(calendar.time)
            return expectedDate == newCompletionDate
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun getStreakMilestones(currentStreak: Int): List<Int> {
        // Return milestone numbers that were just reached
        val milestones = listOf(3, 7, 14, 21, 30, 50, 100)
        return milestones.filter { milestone ->
            currentStreak == milestone
        }
    }
}
